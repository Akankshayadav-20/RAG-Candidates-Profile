package com.tyss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import com.tyss.security.CustomUserDetailsService;
import com.tyss.security.OAuth2LoginSuccessHandler;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler ;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.userDetailsService(customUserDetailsService)

            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                        "/login",
                        "/register",
                        "/forgot-password",
                        "/reset-password",
                        "/css/**",
                        "/js/**"
                       
                ).permitAll()

                .anyRequest().authenticated()

            )

            .formLogin(form -> form

                .loginPage("/login")

                .loginProcessingUrl("/login")

                .defaultSuccessUrl("/chat", true)

                .failureUrl("/login?error=true")

                .permitAll()

            )
            
            .oauth2Login(oauth -> oauth
            		             .loginPage("/login")
            		             .successHandler(oAuth2LoginSuccessHandler)
            		             
            		
            )

            .logout(logout -> logout

                .logoutUrl("/logout")

                .logoutSuccessUrl("/login?logout=true")

                .invalidateHttpSession(true)

                .deleteCookies("JSESSIONID")

                .permitAll()

            );

        return http.build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}