package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.company.repositories.JobRepository;

//url swagger: http://localhost:8080/swagger-ui/index.html
@Service
public class ListAllJobsByFilterUseCase {


    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter){
       return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);


        
    }
    
}
