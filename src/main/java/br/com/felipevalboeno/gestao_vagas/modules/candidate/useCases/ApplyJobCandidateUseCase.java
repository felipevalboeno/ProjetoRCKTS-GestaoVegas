package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipevalboeno.gestao_vagas.exceptions.JobNotFoundException;
import br.com.felipevalboeno.gestao_vagas.exceptions.UserNotFoundException;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.felipevalboeno.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    // Lógica para o candidato se candidatar a uma vaga
    public void execute(UUID IdCandidate, UUID IdJob) {
        //validar se o candidato e a vaga existem
        this.candidateRepository.findById(IdCandidate)
        .orElseThrow(() -> {
            throw new UserNotFoundException();

        });

        this.jobRepository.findById(IdJob)
        .orElseThrow(() -> {
            throw new JobNotFoundException();

        });
    

    }
    
}
