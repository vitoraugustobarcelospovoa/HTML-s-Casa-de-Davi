package com.thymeleaf.crud.thcrud.Controller;

import com.thymeleaf.crud.thcrud.Model.UserModel;
import com.thymeleaf.crud.thcrud.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //import org.springframework.ui.Model;
    @GetMapping(value = "/register")
    public String getPage(Model model){
        model.addAttribute("user", new UserModel());
        return "index";
    }

    @GetMapping(value = "/users")
    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute UserModel userModel, RedirectAttributes redirectAttributes) {
        userRepository.save(userModel);
        redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");
        return "redirect:/register";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "editar_usuario";
        }
        return "redirect:/register";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable long id, @ModelAttribute UserModel userModel, RedirectAttributes redirectAttributes) {
        Optional<UserModel> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserModel updateUser = existingUser.get();
            updateUser.setName(userModel.getName());
            updateUser.setPassword(userModel.getPassword());
            userRepository.save(updateUser);
            redirectAttributes.addFlashAttribute("message", "Usuário atualizado com sucesso!");
        }
        return "redirect:/register";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable long id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return "Usuário deletado com sucesso!";
        }
        return "Usuário não encontrado!";
    }
}
