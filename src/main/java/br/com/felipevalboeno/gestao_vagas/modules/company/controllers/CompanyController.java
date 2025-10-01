package br.com.felipevalboeno.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipevalboeno.gestao_vagas.exceptions.UserFoundException;
import br.com.felipevalboeno.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.felipevalboeno.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
@Tag(name = "Company Controller", description = "Cadastro da empresa")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity){
        
    try {
       var result = this.createCompanyUseCase.execute(companyEntity);
        return ResponseEntity.ok().body(result);

    } catch (UserFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
        

    }
    
}
