package dmitry.adm.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1)
public class ApiSecurityConfig {

    private final String apiKeyHeader = "apiKey";

    @Value("${adm.api-key-value}")
    private String apiKeyValue;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        ApiKeyAuthFilter apiKeyAuthFilter = new ApiKeyAuthFilter(apiKeyHeader);

        apiKeyAuthFilter.setAuthenticationManager(authentication -> {

            var requestApiKeyValue = (String) authentication.getPrincipal();

            if (!apiKeyValue.equals(requestApiKeyValue)) {
                throw new BadCredentialsException("invalid api key");
            }

            authentication.setAuthenticated(true);

            return authentication;

        });

        httpSecurity.securityMatcher("/devices/**")
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(apiKeyAuthFilter).authorizeHttpRequests().anyRequest().authenticated()
        ;

        return httpSecurity.build();
    }

}
