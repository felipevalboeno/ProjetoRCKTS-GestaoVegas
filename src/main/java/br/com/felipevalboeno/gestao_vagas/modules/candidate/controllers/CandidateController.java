package br.com.felipevalboeno.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.CandidateEntity;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/candidate")
public class CandidateController {


   
    @PostMapping("/")
    public void create(@Valid @RequestBody CandidateEntity candidateEntity){
        System.out.println("Candidato");
        System.out.println(candidateEntity.getEmail());
          System.out.println(candidateEntity.getName());
            System.out.println(candidateEntity.getDescription());
              System.out.println(candidateEntity.getUsername());

    }
    
}
