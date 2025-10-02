package br.com.felipevalboeno.gestao_vagas.modules.candidate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/candidate")
public class CandidateViewController {

    @GetMapping("/job/applied/view")
    public String showAppliedJobsPage() {
        return "candidate/applied-jobs"; // aponta para templates/candidate/applied-jobs.html
    }
}
