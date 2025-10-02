package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;

import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.dto.AppliedJobResponseDTO;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.repository.ApplyJobRepository;

@Service
public class ListJobsAppliedByCandidateUseCase {

    @Autowired
    private ApplyJobRepository applyJobRepository;

/**
     * Seguindo a arquitetura clean/hexagonal, não acessamos o repositório diretamente no controller.
     * Método que faz a lógica de negócio: buscar as inscrições do candidato e mapeia para o DTO.
     * applyJobRepository.findByCandidateId(candidateId) → busca todas as inscrições do candidato.
     * stream().map(...) → transforma cada ApplyJobEntity em AppliedJobResponseDTO.
     * Corrigido o getter: getCompanyEntity() → porque no JobEntity o campo é companyEntity.
     */
   public List<AppliedJobResponseDTO> execute(UUID candidateId) {
    //busca todas as vagas aplicadas de um candidato específico, e guarda na lista appliedJobs
    var appliedJobs = applyJobRepository.findByCandidateId(candidateId);

 
    /** 
     * transforma a lista de ApplyJobEntity em AppliedJobResponseDTO
     * Ou seja, Pega cada inscrição (ApplyJobEntity) e cria um novo AppliedJobResponseDTO com os dados relevantes.
     */
    return appliedJobs.stream()
        .map(apply -> new AppliedJobResponseDTO(
                apply.getJobEntity().getId(),
                apply.getJobEntity().getDescription(),
                apply.getJobEntity().getCompanyEntity().getName(),
                apply.getCreatedAt()
        ))
        .toList();
}
}
