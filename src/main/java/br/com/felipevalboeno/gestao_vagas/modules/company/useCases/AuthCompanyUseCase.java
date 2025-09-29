package br.com.felipevalboeno.gestao_vagas.modules.company.useCases;


import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.felipevalboeno.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.felipevalboeno.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.felipevalboeno.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    
@Autowired
private CompanyRepository companyRepository;

@Autowired
private PasswordEncoder passwordEncoder;

public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
    var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
    .orElseThrow(
        () ->{
            throw new UsernameNotFoundException("Username/Password incorrect");
        });

        var passwordMatches =this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        //se as senhas forem diferentes, retorna erro
        if(!passwordMatches){
            throw new AuthenticationException();
        }

        //se forem iguais, retorna token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
        .withSubject(company.getId().toString())
        .withExpiresAt(expiresIn)
        .withClaim("roles", Arrays.asList("COMPANY"))
        .sign(algorithm);

        var roles = Arrays.asList("COMPANY");

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
        .access_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .roles(roles)
        .build();


    return authCompanyResponseDTO;


}


}
