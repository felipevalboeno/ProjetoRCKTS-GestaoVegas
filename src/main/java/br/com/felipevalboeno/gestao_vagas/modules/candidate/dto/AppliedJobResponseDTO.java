package br.com.felipevalboeno.gestao_vagas.modules.candidate.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppliedJobResponseDTO {

    @Schema(example = "550e8400-e29b-41d4-a716-446655440000", description = "ID da vaga aplicada")
    private UUID jobId;

    @Schema(example = "Desenvolvedor Java Pleno", description = "Descrição da vaga")
    private String description;

    @Schema(example = "Empresa X", description = "Nome da empresa")
    private String name;

    @Schema(description = "Data da inscrição")
    private LocalDateTime appliedAt;
}