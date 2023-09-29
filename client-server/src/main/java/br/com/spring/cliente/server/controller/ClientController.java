package br.com.spring.cliente.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.cliente.server.config.WebClientConfig;
import reactor.core.publisher.Mono;

@RestController
public class ClientController {

	
	@Autowired
	private WebClientConfig webClientConfig;
	
		@GetMapping("home")
		public Mono<String> home(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client
								,@AuthenticationPrincipal OidcUser oidcUser ){
			 return Mono.just("""
				        <h2> Access Token: %s </h2>
				        <h2> Refresh Token: %s </h2>
				        <h2> Id Token: %s </h2>
				        <h2> Claims: %s </h2>
				          """.formatted(client.getAccessToken().getTokenValue(),
				        client.getRefreshToken().getTokenValue(),
				        oidcUser.getIdToken().getTokenValue(),
				        oidcUser.getClaims()));
		}
		
		
		 @GetMapping("tasks")
		  public Mono<String> getTasks(@RegisteredOAuth2AuthorizedClient("client-server-oidc") OAuth2AuthorizedClient client) {
		    
			 return this.webClientConfig.createWebClient()
								       .get()
								       .uri("/tasks")
								       .header("Authorization", "Bearer %s".formatted(client.getAccessToken().getTokenValue()))
								       .retrieve()
								       .bodyToMono(String.class);
		  }
}
