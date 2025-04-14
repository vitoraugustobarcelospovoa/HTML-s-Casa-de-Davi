package com.thymeleaf.crud.thcrud.Controller;

import com.thymeleaf.crud.thcrud.Model.BeneficiarioModel;
import com.thymeleaf.crud.thcrud.Repository.BeneficiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BeneficiarioController {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @GetMapping("/beneficiario/novo")
    public String novoBeneficiario(Model model) {
        model.addAttribute("beneficiario", new BeneficiarioModel());
        return "beneficiario_register";
    }

    @PostMapping("/beneficiario/salvar")
    public String salvarBeneficiario(@ModelAttribute BeneficiarioModel beneficiario) {
        beneficiarioRepository.save(beneficiario);
        return "redirect:/beneficiario/novo";
    }
}