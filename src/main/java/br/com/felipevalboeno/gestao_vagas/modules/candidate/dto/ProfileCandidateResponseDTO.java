package br.com.felipevalboeno.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Pessoa desenvolvedora Java")
    private String description;

     @Schema(example = "Maria")
    private String username;

     @Schema(example = "maria@gmail.com")
    private String email;
    private UUID ID;

     @Schema(example = "Maria de Souza")
    private String name;
    
}
