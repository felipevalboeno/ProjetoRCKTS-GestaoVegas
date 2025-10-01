package br.com.felipevalboeno.gestao_vagas.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Controller responsável pela autenticação de candidatos.
 * 
 * Permite:
 * - Gerar token JWT para candidatos registrados
 * 
 * Todos os endpoints desta classe são públicos e destinados apenas
 * para autenticação.
 * 
 * @author Felipe
 * @version 1.0
 * @since 2025-10-01
 */
@RestController
@RequestMapping("/candidate")
@Tag(name = "Auth Candidate Controller", description = "Autenticação do candidato")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    /**
     * Endpoint para autenticar um candidato.
     * 
     * Recebe as credenciais do candidato (usuario e senha) e retorna um token JWT
     * caso a autenticação seja bem-sucedida.
     * 
     * @param authCandidateRequestDTO objeto contendo usuario e senha do candidato
     * @return ResponseEntity com token JWT em caso de sucesso, ou mensagem de erro com status 401 se falhar
     * @throws Exception se ocorrer erro durante a autenticação
     */
    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            var token = this.authCandidateUseCase.execute(authCandidateRequestDTO);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
    
}
