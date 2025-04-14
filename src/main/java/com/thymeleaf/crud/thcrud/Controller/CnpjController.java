package com.thymeleaf.crud.thcrud.Controller;

import com.thymeleaf.crud.thcrud.dto.ReceitaWsResponse;
import com.thymeleaf.crud.thcrud.Service.ReceitaWsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cnpj")
public class CnpjController {

    @Autowired
    private ReceitaWsService receitaWsService;

    @GetMapping("/{cnpj}")
    public ReceitaWsResponse consultarCnpj(@PathVariable String cnpj) {
        return receitaWsService.consultarCnpj(cnpj);
    }
}