package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.SupabaseUseCase;
import com.rocky.appstockdata.domain.dto.TokenResponseDTO;
import com.rocky.appstockdata.domain.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SupabaseService implements SupabaseUseCase {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

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
}
