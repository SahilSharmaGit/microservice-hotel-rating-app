package com.user.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import com.user.config.interceptor.RestTemplateInterceptor;

@Configuration
public class BeanConfiguration {

	@Autowired
	private ClientRegistrationRepository registrationrepository;

	@Autowired
	private OAuth2AuthorizedClientRepository clientRepository;

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new RestTemplateInterceptor(manager(registrationrepository, clientRepository)));
		restTemplate.setInterceptors(interceptors);
		return restTemplate;

	}

	@Bean
	public OAuth2AuthorizedClientManager manager(ClientRegistrationRepository registrationrepository,
			OAuth2AuthorizedClientRepository clientRepository) {

		OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials()
				.build();
		DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
				registrationrepository, clientRepository);

		defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
		return defaultOAuth2AuthorizedClientManager;
	}
}
