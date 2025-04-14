package com.thymeleaf.crud.thcrud.Controller;

import com.thymeleaf.crud.thcrud.Model.BeneficiarioModel;
import com.thymeleaf.crud.thcrud.Repository.BeneficiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiarios")
public class BeneficiarioRestController {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @GetMapping
    public List<BeneficiarioModel> buscarBeneficiarios(
            @RequestParam(required = false) String cpfCnpj,
            @RequestParam(required = false) String nome) {

        boolean cpfCnpjVazio = (cpfCnpj == null || cpfCnpj.trim().isEmpty());
        boolean nomeVazio = (nome == null || nome.trim().isEmpty());

        // Se ambos os filtros (cpfCnpj e nome) estiverem vazios, retorna todos os beneficiários
        if (cpfCnpjVazio && nomeVazio) {
            return beneficiarioRepository.findAll();
        }

        // Se um dos parâmetros estiver nulo, substitui por string vazia
        String filtroCpfCnpj = cpfCnpj != null ? cpfCnpj : "nulo";
        String filtroNome = nome != null ? nome : "nulo";

        // Chama o método de busca com filtros
        return beneficiarioRepository.buscarPorFiltro(filtroCpfCnpj, filtroNome);
    }
}