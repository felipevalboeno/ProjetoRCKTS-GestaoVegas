package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.company.repositories.JobRepository;


@Service
public class ListAllJobsUseCase {
    

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter){
       return this.jobRepository.findAllJobs();


        
    }
}
