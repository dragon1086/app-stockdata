package com.rocky.appstockdata.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocky.appstockdata.application.port.in.SupabaseUseCase;
import com.rocky.appstockdata.domain.dto.DealTrainingSourceDTO;
import com.rocky.appstockdata.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupabaseService implements SupabaseUseCase {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    private final ObjectMapper objectMapper = new ObjectMapper();

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
            ResponseEntity<String> response;
            if (dto.getId() != null) {
                // Update existing record
                headers.set("Prefer", "resolution=merge-duplicates,return=representation");
                url += "?id=eq." + dto.getId();
                method = HttpMethod.PATCH;
            } else {
                method = HttpMethod.POST;
            }

            HttpEntity<DealTrainingSourceDTO> request = new HttpEntity<>(dto, headers);
            response = restTemplate.exchange(url, method, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode root = objectMapper.readTree(response.getBody());
                Long id = root.get(0).get("id").asLong();

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

        //fixme : 매수나 매도 하면 그래프에 토글버튼 자꾸 새로 생김.
    }

    public List<DealTrainingSourceDTO> getDealTrainingHistoryByUserId(UserDTO user) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + user.getAccessToken());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        String url = supabaseUrl + "/rest/v1/deal_training_history?user_id=eq." + user.getId();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to get history: " + response.getBody());
        }

        try {
            return objectMapper.readValue(response.getBody(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, DealTrainingSourceDTO.class));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }
}
