package br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.felipevalboeno.gestao_vagas.modules.company.entities.JobEntity;
import br.com.felipevalboeno.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should be able to apply for a job")
    public void should_not_be_able_to_apply_for_a_job_if_candidate_does_not_exist() {

        try {
             applyJobCandidateUseCase.execute(null, null);

        } catch (Exception e) {
            assert(e instanceof br.com.felipevalboeno.gestao_vagas.exceptions.UserNotFoundException);
        }
       

    }


    @Test
    @DisplayName("Should not be able to apply for a job if job does not exist")
    public void should_not_be_able_to_apply_job_if_job_does_not_exist() {
        var idCandidate = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        
      when(candidateRepository.findById(idCandidate))
      .thenReturn(Optional.of(candidate));

       
      try {
        applyJobCandidateUseCase.execute(idCandidate, null);

        } catch (Exception e) {
            assert(e instanceof br.com.felipevalboeno.gestao_vagas.exceptions.JobNotFoundException);
        }

    }

    @Test
    public void should_be_able_to_apply_for_a_new_job() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();


        var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob)
        .build();

       // applyJob.setId(UUID.randomUUID());


    var applyJobCreated = ApplyJobEntity.builder()
        .id(UUID.randomUUID())
        .build();


    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
    when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

      //when(applyJobRepository.save(applyJob))
      //.thenReturn(new ApplyJobEntity());

      //when(applyJobRepository.save(any(ApplyJobEntity.class)))
    //.thenReturn(new ApplyJobEntity());

    
      when(applyJobRepository.save(applyJob))
    .thenReturn(applyJobCreated);




         var result = applyJobCandidateUseCase.execute(idCandidate, idJob);
      
        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    

    }
    
}
