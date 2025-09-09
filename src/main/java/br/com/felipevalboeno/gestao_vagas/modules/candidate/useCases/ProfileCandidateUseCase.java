package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.felipevalboeno.gestao_vagas.exceptions.UserNotFoundException;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate){
        var candidate = this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        var candidateDTO = ProfileCandidateResponseDTO.builder()
        .ID(candidate.getId())
        .name(candidate.getName())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .description(candidate.getDescription())
        .build();

         return candidateDTO;

    }
    
}
