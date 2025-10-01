package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ListJobsAppliedByCandidateUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(UUID idCandidate) {
        return this.jobRepository.findJobsAppliedByCandidateId(idCandidate);
    }
}
