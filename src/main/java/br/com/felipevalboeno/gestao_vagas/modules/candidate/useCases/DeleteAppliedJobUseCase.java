package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.repository.CandidateRepository;

@Service
public class DeleteAppliedJobUseCase {

    private final CandidateRepository candidateRepository;
    private final ApplyJobRepository applyJobRepository;

    public DeleteAppliedJobUseCase(CandidateRepository candidateRepository, ApplyJobRepository applyJobRepository) {
        this.candidateRepository = candidateRepository;
        this.applyJobRepository = applyJobRepository;
    }

    @Transactional
    public void execute(UUID candidateId, UUID jobId) {

        // Verifica se o candidato existe
        candidateRepository.findById(candidateId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidato não encontrado"));

        // Busca a inscrição específica do candidato na vaga
        ApplyJobEntity appliedJob = applyJobRepository
            .findByCandidateIdAndJobId(candidateId, jobId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada"));

        // Deleta a inscrição
        applyJobRepository.delete(appliedJob);
    }
}
