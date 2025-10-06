package br.com.felipevalboeno.gestao_vagas.modules.company.useCases;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class UpdateJobUseCase {

    // Instanciando JobRepository (será imutável e a classe UpdateJobUseCase precisa dele)
    private final JobRepository jobRepository;

    //Injeção de dependência via construtor
    public UpdateJobUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobEntity execute(UUID companyId, UUID jobId, JobEntity updatedJob) {
        JobEntity existingJob = jobRepository.findById(jobId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vaga não encontrada"));

        if (!existingJob.getCompanyId().equals(companyId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: você não pode atualizar essa vaga");
        }

        // Atualiza apenas os campos permitidos
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setBenefits(updatedJob.getBenefits());
        existingJob.setLevel(updatedJob.getLevel());

        return jobRepository.save(existingJob);
    }
}
