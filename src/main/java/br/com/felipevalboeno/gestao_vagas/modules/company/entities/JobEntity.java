package br.com.felipevalboeno.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.entity.CandidateEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;//onstrutor com argumentos
import lombok.Builder;
import lombok.Data;//getter and setter
import lombok.NoArgsConstructor;//cnstrutor sem argumentos

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @ManyToMany(mappedBy = "jobsApplied")
    private List<CandidateEntity> candidates;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Vaga para pessoa desenvolvedora Jr")
    private String description;

    @Schema(example = "Gympass, VA/VR, Plano de saúde")
    private String benefits;

    @NotBlank(message = "This field is required")
    @Schema(example = "JUNIOR")
    private String level;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyEntity companyEntity;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
