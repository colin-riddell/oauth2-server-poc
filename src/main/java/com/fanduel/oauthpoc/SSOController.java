package com.fanduel.oauthpoc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SSOController {
    @GetMapping("/login")
    public String login(
            @RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam("scope") String scope,
            @RequestParam("state") String state,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("nonce") String nonce) {
        return String.format("redirect:/oauth2/authorize?response_type=%s&client_id=%s&scope=%s&state=%s&redirect_uri=%s&nonce=%s",
                responseType, clientId, scope, state, redirectUri, nonce
        );
    }
}
