package com.rocky.appstockdata.adaptor.web.in;

import com.rocky.appstockdata.application.port.in.SupabaseUseCase;
import com.rocky.appstockdata.domain.dto.TokenVerificationRequest;
import com.rocky.appstockdata.domain.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class AuthApiV1 {
    private SupabaseUseCase supabaseUseCase;

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    public AuthApiV1(SupabaseUseCase supabaseUseCase) {
        this.supabaseUseCase = supabaseUseCase;
    }

    @GetMapping("/login/google")
    public String login() {
        String authUrl = supabaseUseCase.getAuthorizationUrl();
        return "redirect:" + authUrl;
    }

    @GetMapping("/auth/callback/google")
    public String handleCallback(ModelMap modelMap) {
        modelMap.put("supabaseUrl", supabaseUrl);
        modelMap.put("supabaseAnonKey", supabaseKey);
        return "supabaseCallback";
    }

    @PostMapping("/auth/verify-token")
    @ResponseBody
    public String verifyToken(@RequestBody TokenVerificationRequest request, HttpSession session) {
        try {
            UserDTO user = supabaseUseCase.verifyAndGetUser(request.getAccessToken());
            session.setAttribute("sessionUser", user);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/logout/google")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
