package br.com.felipevalboeno.gestao_vagas.modules.candidate.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.entity.CandidateEntity;



public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID>{
    
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
    Optional<CandidateEntity> findByUsername(String username);

}
