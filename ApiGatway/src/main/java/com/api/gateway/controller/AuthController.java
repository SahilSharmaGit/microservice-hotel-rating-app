package com.api.gateway.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.gateway.models.AuthResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@GetMapping("/login")
	public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
			@AuthenticationPrincipal OidcUser user, Model model) {

		log.info("User email id {}", user.getEmail());

		AuthResponse response = new AuthResponse();
		response.setUserId(user.getEmail());
		response.setAccessToken(client.getAccessToken().getTokenValue());
		response.setRefreshToken(client.getRefreshToken().getTokenValue());
		response.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());
		List<String> authorities = user.getAuthorities().stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
		response.setAuthorities(authorities);
		
		return ResponseEntity.ok(response);
	}

}
