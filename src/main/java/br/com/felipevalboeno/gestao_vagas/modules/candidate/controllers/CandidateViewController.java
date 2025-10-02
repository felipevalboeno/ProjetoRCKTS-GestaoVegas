package br.com.felipevalboeno.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.felipevalboeno.gestao_vagas.modules.candidate.dto.AppliedJobResponseDTO;
import br.com.felipevalboeno.gestao_vagas.modules.candidate.useCases.ListJobsAppliedByCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/candidate")
public class CandidateViewController {

    @Autowired
    private ListJobsAppliedByCandidateUseCase listJobsAppliedByCandidateUseCase;

    @GetMapping("/job/applied/view")
    public String showAppliedJobsPage(Model model, HttpServletRequest request) {
        // Você pode carregar as vagas aplicadas direto no backend
        UUID candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());
        List<AppliedJobResponseDTO> jobs = listJobsAppliedByCandidateUseCase.execute(candidateId);
        model.addAttribute("jobs", jobs);
        return "candidate/applied-jobs"; // template Thymeleaf
    }

    
}
