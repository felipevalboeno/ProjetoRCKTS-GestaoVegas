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

   public List<AppliedJobResponseDTO> execute(UUID candidateId) {
    var appliedJobs = applyJobRepository.findByCandidateId(candidateId);

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
