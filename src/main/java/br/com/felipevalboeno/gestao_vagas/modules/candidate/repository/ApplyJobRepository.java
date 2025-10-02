package br.com.felipevalboeno.gestao_vagas.modules.candidate.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.entity.ApplyJobEntity;

public interface ApplyJobRepository  extends JpaRepository<ApplyJobEntity, UUID>{

    List<ApplyJobEntity> findByCandidateId(UUID candidateId);

    
}
