package br.com.felipevalboeno.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import br.com.felipevalboeno.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import br.com.felipevalboeno.gestao_vagas.modules.company.useCases.DeleteJobUseCase;
import br.com.felipevalboeno.gestao_vagas.modules.company.useCases.ListAllJobsByCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @Autowired
    private ListAllJobsByCompanyUseCase listAllJobsByCompanyUseCase;

    @Autowired
    private DeleteJobUseCase deleteJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')") // so a company pode criar vaga
    @Tag(name = "Vagas", description = "Informações sobre vagas")
    @Operation(summary = "Cadastro de vagas", description = "Endpoint para cadastro de vagas (somente para empresas).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class)) }),
    })

    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {

        var companyId = request.getAttribute("company_id"); // recupera o atriuto

        // jobEntity.setCompanyId(UUID.fromString(companyId.toString())); //seta o
        // companyId convertendo pra UUID

        try {
            var jobEntity = JobEntity.builder()
                    .benefits(createJobDTO.getBenefits())
                    .companyId(UUID.fromString(companyId.toString()))
                    .description(createJobDTO.getDescription())
                    .level(createJobDTO.getLevel())
                    .build();

            var result = this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('COMPANY')") // so a company pode criar vaga
    @Tag(name = "Vagas", description = "Listagem das vagas")
    @Operation(summary = "Listagem de vagas", description = "Endpoint para listagem de vagas (somente para empresas).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class)) }),
    })

    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> listByCompany(HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");
        var result = this.listAllJobsByCompanyUseCase.execute(UUID.fromString(companyId.toString()));
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/company/{jobId}")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Vagas", description = "Excluir vaga")
    @Operation(summary = "Excluir vaga", description = "Endpoint para excluir vaga (somente para a empresa dona da vaga).")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<String> deleteJob(@PathVariable UUID jobId, HttpServletRequest request) {
        UUID companyId = UUID.fromString(request.getAttribute("company_id").toString());
        deleteJobUseCase.execute(companyId, jobId);
        return ResponseEntity.ok("Vaga deletada com sucesso");
    }


}
