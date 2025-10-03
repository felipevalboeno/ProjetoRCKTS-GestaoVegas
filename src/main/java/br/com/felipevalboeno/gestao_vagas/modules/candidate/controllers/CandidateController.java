package br.com.felipevalboeno.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.dto.AppliedJobResponseDTO;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases.ListJobsAppliedByCandidateUseCase;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


/**
 * Controller responsável pelos endpoints do candidato.
 * 
 * Permite:
 * - Cadastro de candidatos
 * - Consulta de perfil do candidato
 * - Listagem de vagas com filtro
 * - Inscrição em vagas
 * 
 * Todos os endpoints de perfil e vagas exigem autenticação JWT.
 * 
 * @author Felipe
 * @version 1.0
 * @since 2025-10-01
 */
@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

  @Autowired
  private ListJobsAppliedByCandidateUseCase listJobsAppliedByCandidateUseCase;
  
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @Autowired
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;


  //#region Cadastro de candidato
   /**
     * Endpoint para cadastrar um novo candidato.
     * 
     * @param candidateEntity objeto contendo os dados do candidato
     * @return ResponseEntity com o candidato criado ou mensagem de erro
     * @throws Exception se o candidato já existir ou houver erro na criação
     */
  @PostMapping("/")
  @Operation(summary = "Cadastro de candidato", description = "Endpoint para cadastrar um candidato")
  @ApiResponses({
  @ApiResponse(responseCode = "200", content = {
    @Content(
      schema = @Schema(implementation = CandidateEntity.class))
  }),
  @ApiResponse(responseCode = "400", description = "Usuário já existe.")

})
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {

    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {

      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }
//#endregion

  //#region Perfil do candidato
  /**
     * Endpoint para consultar o perfil do candidato autenticado.
     * 
     * @param request HttpServletRequest contendo o atributo "candidate_id"
     * @return ResponseEntity com os dados do perfil do candidato ou mensagem de erro
     * @throws Exception se o candidato não for encontrado
     */
  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  //@Tag(name = "Candidato", description = "Informações do candidato")
  @Operation(summary = "Perfil do candidato", description = "Endpoint para buscar as informações do perfil do candidato")
  @ApiResponses({
  @ApiResponse(responseCode = "200", content = {
    @Content(
      schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
  }),
  @ApiResponse(responseCode = "400", description = "User not found")

})
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> get(HttpServletRequest request) {

    var idCandidate = request.getAttribute("candidate_id");
    try {
      var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
      return ResponseEntity.ok().body(profile);

    } catch (Exception e) {

      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }
//#endregion

  //#region Listar vagas por filtro
  /**
     * Endpoint para listar todas as vagas que contenham o filtro na descrição.
     * 
     * @param filter texto usado para filtrar vagas por descrição
     * @return Lista de JobEntity que correspondem ao filtro
     */
@GetMapping("/job")
@PreAuthorize("hasRole('CANDIDATE')")
//@Tag(name = "Candidato", description = "Informações do candidato")
@Operation(summary = "Listar vagas por candidato",
 description = "Endpoint para listar todas as vagas que contenham o filtro na descrição")
@ApiResponses({
  @ApiResponse(responseCode = "200", content = {
    @Content(
      array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
  })

})

@SecurityRequirement(name = "jwt_auth")
public List<JobEntity> findJobByFilter(@RequestParam String filter) {

  return this.listAllJobsByFilterUseCase.execute(filter);

}
//#endregion

//#region Inscrever candidato em uma vaga
    /**
     * Endpoint para inscrever o candidato em uma vaga específica.
     * 
     * @param request HttpServletRequest contendo o atributo "candidate_id"
     * @param idJob UUID da vaga em que o candidato deseja se inscrever
     * @return ResponseEntity com confirmação da inscrição ou mensagem de erro
     * @throws Exception se houver problema ao inscrever o candidato
     */
@PostMapping("/job/apply")
@PreAuthorize("hasRole('CANDIDATE')")
@SecurityRequirement(name = "jwt_auth")
@Operation(summary = "Inscrição do candidato para uma vaga",
 description = "Endpoint responsável por realizar a inscrição do candidato em uma vaga")
public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
 
  var idCandidate = request.getAttribute("candidate_id");

try {
    var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);

    return ResponseEntity.ok().body(result);

  } catch (Exception e) {
  return ResponseEntity.badRequest().body(e.getMessage());
}
}


//#region Listar todas vagas aplicadas pelo candidato
/**
 * 
 * Expõe o UseCase via HTTP para que o front-end possa listar as vagas que o candidato aplicou.
 * Endpoint seguro: exige JWT do candidato.
 * @GetMapping("/job/applied") → URL clara e RESTful.
 * @PreAuthorize("hasRole('CANDIDATE')") → garante que só candidatos logados podem acessar.
 * HttpServletRequest request.getAttribute("candidate_id") → pega o ID do candidato a partir do token JWT.
 * Retorno ResponseEntity<List<AppliedJobResponseDTO>> → lista tipada de DTO, evita problemas de serialização no Swagger.
 */

@GetMapping("/job/applied")
@PreAuthorize("hasRole('CANDIDATE')")
@Operation(summary = "Listar vagas aplicadas",
           description = "Lista todas as vagas em que o candidato já se inscreveu")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppliedJobResponseDTO.class)))),
    @ApiResponse(responseCode = "400", description = "Erro na listagem")
})
@SecurityRequirement(name = "jwt_auth")
public ResponseEntity<List<AppliedJobResponseDTO>> listAppliedJobs(HttpServletRequest request) {
    var idCandidate = UUID.fromString(request.getAttribute("candidate_id").toString());
    var appliedJobs = this.listJobsAppliedByCandidateUseCase.execute(idCandidate);

    return ResponseEntity.ok(appliedJobs);

    
}




}
