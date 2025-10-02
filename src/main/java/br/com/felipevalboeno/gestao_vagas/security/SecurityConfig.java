package br.com.felipevalboeno.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private SecurityCompanyFilter securityCompanyFilter;

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;


    private static final String[] PERMIT_ALL_LIST = {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/actuator/**"


    };

    @Bean //indica que esse método é usado pra definir um objeto gerenciado pelo spring
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/candidate/").permitAll()
            .requestMatchers("/company/").permitAll()
            .requestMatchers("/company/auth").permitAll()
            .requestMatchers(PERMIT_ALL_LIST).permitAll()
            .requestMatchers("/candidate/auth").permitAll()
            .requestMatchers("/candidate/job/applied/view").permitAll();
            
            auth.anyRequest().authenticated();
        })
        .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
        .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class);
        
        
        ;
        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
