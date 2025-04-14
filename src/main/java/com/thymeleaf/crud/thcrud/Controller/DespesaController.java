package com.thymeleaf.crud.thcrud.Controller;

import com.thymeleaf.crud.thcrud.Model.DespesaModel;
import com.thymeleaf.crud.thcrud.Model.UserModel;
import com.thymeleaf.crud.thcrud.Repository.DespesaRepository;
import com.thymeleaf.crud.thcrud.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.thymeleaf.crud.thcrud.Model.Document;
import com.thymeleaf.crud.thcrud.Repository.DocumentRepository;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/despesas")
public class DespesaController {

  @Autowired
  private DespesaRepository despesaRepository;

  @Autowired
  private DocumentRepository documentRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public String listarDespesas(Model model) {
    List<DespesaModel> despesas = despesaRepository.findAll();
    model.addAttribute("despesas", despesas);
    return "despesa-form";
  }

  @GetMapping("/nova")
  public String novaDespesaForm(Model model) {
    model.addAttribute("despesa", new DespesaModel());
    return "despesa-form";
  }

  @PostMapping("/salvar")
  public String salvarDespesa(@ModelAttribute DespesaModel despesaModel,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("title") String title) throws IOException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    UserModel usuario = userRepository.findByUsername(username).orElseThrow();

    despesaModel.setUsuario(usuario);

    if (!file.isEmpty()) {
      Document document = new Document();
      document.setTitle(title);
      document.setFileData(file.getBytes());
      documentRepository.save(document);
      despesaModel.setDocumento(document);
    }

    despesaRepository.save(despesaModel);
    return "redirect:/despesas/nova";
  }

  @GetMapping("/editar/{id}")
  public String editarDespesa(@PathVariable Long id, Model model) {
    DespesaModel despesa = despesaRepository.findById(id).orElseThrow();
    model.addAttribute("despesa", despesa);
    return "despesa-form";
  }

  @PostMapping("/atualizar/{id}")
  public String atualizarDespesa(@PathVariable Long id, @ModelAttribute DespesaModel despesaAtualizada) {
    DespesaModel despesaExistente = despesaRepository.findById(id).orElseThrow();

    despesaAtualizada.setId(id);
    despesaAtualizada.setUsuario(despesaExistente.getUsuario());

    despesaRepository.save(despesaAtualizada);
    return "redirect:/despesas/nova";
  }

  @GetMapping("/deletar/{id}")
  public String deletarDespesa(@PathVariable Long id) {
    despesaRepository.deleteById(id);
    return "redirect:/despesas/nova";
  }
}
