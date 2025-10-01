package br.com.felipevalboeno.gestao_vagas.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    
   

    List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);

    List<JobEntity> findByCompanyId(UUID companyId);

    
@Query("SELECT j FROM JobEntity j " +
           "JOIN j.candidates c " +
           "WHERE c.id = :idCandidate")
    List<JobEntity> findJobsAppliedByCandidateId(UUID idCandidate);
}
