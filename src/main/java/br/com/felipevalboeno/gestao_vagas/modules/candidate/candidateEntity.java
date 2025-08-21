package br.com.felipevalboeno.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


//@Table(name = "candidate")
//ao persistir com entity, será entendido que tudo abaixo é uma culuna no banco
@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)//cria o id automatico ao ter um novo dado
    private UUID id;

    private String name;

  
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
    private String username;

    @Email(message = "O campo [email] deve conter um e-mail válido.")
    private String email;
    
    @Length(min = 10, max = 100, message = "A senha deve conter entre (10) e (100) caracteres.")
    private String password;
    private String description;
    private String curriculum;

   @CreationTimestamp
    private LocalDateTime createdAt;
    
}
