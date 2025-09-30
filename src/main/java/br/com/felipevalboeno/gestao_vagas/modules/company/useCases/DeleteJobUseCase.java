package br.com.felipevalboeno.gestao_vagas.modules.company.useCases;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class DeleteJobUseCase {
    
private final JobRepository jobRepository;

    public DeleteJobUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void execute(UUID companyId, UUID jobId) {
        JobEntity job = jobRepository.findById(jobId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vaga não encontrada"));

        if (!job.getCompanyId().equals(companyId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: você não pode deletar essa vaga");
        }

        jobRepository.delete(job);
    }
}