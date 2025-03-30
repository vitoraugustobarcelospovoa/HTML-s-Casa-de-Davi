package com.thymeleaf.crud.thcrud.Controller;

import com.thymeleaf.crud.thcrud.Model.UserModel;
import com.thymeleaf.crud.thcrud.Repository.UserRepository;
import com.thymeleaf.crud.thcrud.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService; // Usando o serviço de criptografia da senha

  @GetMapping(value = "/register")
  public String getPage(Model model){
    model.addAttribute("user", new UserModel());
    return "index";  // Página de registro
  }

  @GetMapping(value = "/users")
  public List<UserModel> getUsers() {
    return userRepository.findAll();
  }

  @PostMapping(value = "/save")
  public String saveUser(@ModelAttribute UserModel userModel, RedirectAttributes redirectAttributes) {
    // Criptografando a senha antes de salvar
    userModel.setPassword(userService.encryptPassword(userModel.getPassword()));
    userRepository.save(userModel);
    redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");
    return "redirect:/register";
  }

  @PutMapping(value = "/update/{id}")
  public String updateUser(@PathVariable long id, @RequestBody UserModel userModel){
    UserModel updateUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    updateUser.setName(userModel.getName());
    updateUser.setPassword(userModel.getPassword()); // Certifique-se de que a senha seja criptografada
    userRepository.save(updateUser);
    return "Updated...";
  }

  @DeleteMapping(value = "/delete/{id}")
  public String deleteUser(@PathVariable long id){
    UserModel deleteUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    userRepository.delete(deleteUser);
    return "Deleted user with id: "+id;
  }
}
