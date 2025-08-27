package br.com.felipevalboeno.gestao_vagas.modules.company.controllers;



import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipevalboeno.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company/job")
public class JobController {
    
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')") // so a company pode criar vaga
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request){
        
        var companyId = request.getAttribute("company_id"); // recupera o atriuto
        
        //jobEntity.setCompanyId(UUID.fromString(companyId.toString())); //seta o companyId convertendo pra UUID
        
       var jobEntity = JobEntity.builder()
            .benefits(createJobDTO.getBenefits())
            .companyId(UUID.fromString(companyId.toString()))
            .description(createJobDTO.getDescription())
            .level(createJobDTO.getLevel())
            .build(); 


        return this.createJobUseCase.execute(jobEntity);

    }
}
