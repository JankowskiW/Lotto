package pl.wj.lotto.infrastructure.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.wj.lotto.domain.user.port.in.UserServicePort;
import pl.wj.lotto.infrastructure.security.JwtAuthTokenFilter;
import pl.wj.lotto.infrastructure.security.LoginUserDetailsService;
import pl.wj.lotto.infrastructure.security.SecurityService;
import pl.wj.lotto.infrastructure.security.adapter.SecurityServiceAdapter;
import pl.wj.lotto.infrastructure.security.port.in.SecurityServicePort;
import pl.wj.lotto.infrastructure.security.properties.SecurityProperties;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthTokenFilter jwtAuthTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityServicePort securityServicePort(AuthenticationManager authenticationManager, SecurityProperties securityProperties) {
        SecurityService securityService = new SecurityService(authenticationManager, securityProperties);
        return new SecurityServiceAdapter(securityService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserServicePort userServicePort) {
        return new LoginUserDetailsService(userServicePort);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/users/login/**").permitAll()
                        .requestMatchers("/users/register/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .anyRequest().authenticated())
                .headers().frameOptions().disable()
                .and().httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .and().addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
