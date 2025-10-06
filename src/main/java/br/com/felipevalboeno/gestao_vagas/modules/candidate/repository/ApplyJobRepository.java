package br.com.felipevalboeno.gestao_vagas.modules.candidate.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.entity.ApplyJobEntity;

/**
 * Repositório para gerenciar as inscrições de candidatos em vagas.
 * Extende JpaRepository para fornecer operações CRUD básicas.
 */
public interface ApplyJobRepository  extends JpaRepository<ApplyJobEntity, UUID>{

  
      // Busca todas as inscrições de um candidato
    List<ApplyJobEntity> findByCandidateId(UUID candidateId);

    // Busca a inscrição específica do candidato em uma vaga
    Optional<ApplyJobEntity> findByCandidateIdAndJobId(UUID candidateId, UUID jobId);

    
}
