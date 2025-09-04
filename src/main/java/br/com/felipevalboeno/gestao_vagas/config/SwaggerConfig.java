package br.com.felipevalboeno.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/*
@OpenAPIDefinition (
	info = @Info(
		title = "Gestão de Vagas API",
		version = "1.0",
		description = "API para gerenciar vagas em cursos de TI"
	)
)
@SecurityScheme(name = "jwt_auth", scheme = "bearer", bearerFormat = "JWT",
 type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
 */

@Configuration

public class SwaggerConfig {
    
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


    private SecurityScheme createSecurityScheme(){
        return new SecurityScheme()
        .name("jwt_auth")
        .type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer");
    }

}
