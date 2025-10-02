package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;

@Service
public class ListJobsAppliedByCandidateUseCase {

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public List<JobEntity> execute(UUID candidateId) {
        var applications = this.applyJobRepository.findByCandidateId(candidateId);

        return applications.stream()
                .map(ApplyJobEntity::getJobEntity) // pega a vaga vinculada
                .collect(Collectors.toList());
    }
}
