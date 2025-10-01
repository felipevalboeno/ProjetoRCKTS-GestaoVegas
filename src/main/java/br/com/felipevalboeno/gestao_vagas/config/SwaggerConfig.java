package br.com.felipevalboeno.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;


/**
 * Configuração do Swagger/OpenAPI para a API de Gestão de Vagas.
 * 
 * Esta classe define:
 * - Informações gerais da API (título, descrição, versão)
 * - Configuração de autenticação via JWT para documentação e testes
 * 
 * O bean OpenAPI criado aqui é usado pelo Spring para gerar a documentação
 * Swagger automaticamente.
 * 
 * @author Felipe
 * @version 1.0
 * @since 2025-10-01
 */
@Configuration
public class SwaggerConfig {
    
    /**
     * Cria e configura o bean OpenAPI para a aplicação.
     * Define título, descrição, versão e esquema de segurança JWT.
     * 
     * @return OpenAPI configurado para a aplicação
     */
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
        .info(new Info().title("Gestão de vagas")
        .description("API para gerenciar vagas em cursos de TI")
        .version("1.0"))
        .schemaRequirement("jwt_auth", createSecurityScheme());
        //abaixo serve pra atribuir o esquema de segurança globalmente
        /* 
        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        .components(new Components()
        .addSecuritySchemes("Bearer Authentication", createSecurityScheme()));
        */
    }


    /**
     * Cria o esquema de segurança JWT usado para autenticação
     * na documentação da API.
     * 
     * @return SecurityScheme configurado com Bearer JWT
     */
    private SecurityScheme createSecurityScheme(){
        return new SecurityScheme()
        .name("jwt_auth")
        .type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer");
    }

}
