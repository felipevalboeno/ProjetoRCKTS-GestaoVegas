package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipevalboeno.gestao_vagas.exceptions.JobNotFoundException;
import br.com.felipevalboeno.gestao_vagas.exceptions.UserNotFoundException;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.repository.CandidateRepository;
import br.com.felipevalboeno.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    // Lógica para o candidato se candidatar a uma vaga
    public ApplyJobEntity execute(UUID IdCandidate, UUID IdJob) {
        //validar se o candidato e a vaga existem
        this.candidateRepository.findById(IdCandidate)
        .orElseThrow(() -> {
            throw new UserNotFoundException();

        });

        this.jobRepository.findById(IdJob)
        .orElseThrow(() -> {
            throw new JobNotFoundException();

        });

        //candidato se inscreve na vaga
        var applyJob = ApplyJobEntity.builder()
        .candidateId(IdCandidate)
        .jobId(IdJob)
        .build();


        applyJob = applyJobRepository.save(applyJob);
        return applyJob;
    

    }
    
}
