package com.rocky.appstockdata.application.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocky.appstockdata.application.port.in.SupabaseUseCase;
import com.rocky.appstockdata.domain.dto.DealTrainingSourceDTO;
import com.rocky.appstockdata.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SupabaseService implements SupabaseUseCase {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.redirect-uri}")
    private String redirectUri;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    public SupabaseService () {
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper);

        restTemplate  = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(0, messageConverter);
    }
    @Override
    public String getAuthorizationUrl() {
        return supabaseUrl + "/auth/v1/authorize?provider=google&redirect_to=" + redirectUri;
    }

    @Override
    public UserDTO verifyAndGetUser(String accessToken) {
        // Use the Supabase API to verify the access token and get user info
        String userInfoUrl = supabaseUrl + "/auth/v1/user";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("apikey", supabaseKey);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userInfo = response.getBody();
            String id = (String) userInfo.get("id");
            String email = (String) userInfo.get("email");

            String fullName = "";
            if (userInfo.containsKey("user_metadata")) {
                Map<String, Object> userMetadata = (Map<String, Object>) userInfo.get("user_metadata");
                fullName = (String) userMetadata.get("full_name");
            }

            return new UserDTO(id, email, fullName, accessToken);
        } else {
            throw new RuntimeException("Failed to verify user");
        }
    }

    @Override
    public Long upsertDealTrainingSource(UserDTO user, DealTrainingSourceDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + user.getAccessToken());
        headers.set("Prefer", "return=representation");


        String url = supabaseUrl + "/rest/v1/deal_training_history";
        HttpMethod method;
        try {
            ParameterizedTypeReference<List<DealTrainingSourceDTO>> responseType =
                    new ParameterizedTypeReference<List<DealTrainingSourceDTO>>() {};
            if (dto.getId() != null) {
                // Update existing record
                headers.set("Prefer", "resolution=merge-duplicates,return=representation");
                url += "?id=eq." + dto.getId();
                method = HttpMethod.PATCH;
            } else {
                method = HttpMethod.POST;
            }

            HttpEntity<DealTrainingSourceDTO> request = new HttpEntity<>(dto, headers);
            ResponseEntity<List<DealTrainingSourceDTO>> response = restTemplate.exchange(url, method, request, responseType);

            if (response.getStatusCode().is2xxSuccessful()) {
                List<DealTrainingSourceDTO> body = response.getBody();
                Long id = body.get(0).getId();

                String operation = dto.getId() != null ? "updated" : "created";
                log.info("Record " + operation + " with ID: " + id);
                return id;
            } else {
                throw new RuntimeException("Failed to save or update: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            log.error("Exception occurred: " + e.getMessage(),e);
            throw new RuntimeException("Error saving or updating deal training history", e);
        }
    }

    @Override
    public List<DealTrainingSourceDTO> getDealTrainingHistories(String userId, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + accessToken);

        try{
            ParameterizedTypeReference<List<DealTrainingSourceDTO>> responseType =
                    new ParameterizedTypeReference<List<DealTrainingSourceDTO>>() {};
            HttpEntity<?> entity = new HttpEntity<>(headers);
            String url = supabaseUrl + "/rest/v1/deal_training_history?user_id=eq." + userId;
            ResponseEntity<List<DealTrainingSourceDTO>> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to get history: " + response.getBody());
            }

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("HTTP client error: " + e.getStatusCode() + " " + e.getStatusText(), e);
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("HTTP server error: " + e.getStatusCode() + " " + e.getStatusText(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }
}
