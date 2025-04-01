package com.thymeleaf.crud.thcrud.Controller;

import com.thymeleaf.crud.thcrud.Model.UserModel;
import com.thymeleaf.crud.thcrud.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/register")
    public String getPage(Model model) {
        model.addAttribute("user", new UserModel());
        return "user-register";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute UserModel userModel, RedirectAttributes redirectAttributes) {
        String encodedPassword = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encodedPassword);

        userRepository.save(userModel);

        redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");
        return "redirect:/register";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserModel userModel = user.get();
            userModel.setPassword("");
            model.addAttribute("user", userModel);
            return "editar_usuario";
        }
        return "redirect:/register";
    }


    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable long id, @ModelAttribute UserModel userModel, RedirectAttributes redirectAttributes) {
        Optional<UserModel> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            UserModel updateUser = existingUser.get();
            updateUser.setUsername(userModel.getUsername());
            updateUser.setCPF(userModel.getCPF());
            updateUser.setTelefone(userModel.getTelefone());
            updateUser.setCep(userModel.getCep());
            updateUser.setEndereco(userModel.getEndereco());
            updateUser.setBairro(userModel.getBairro());
            updateUser.setCidade(userModel.getCidade());
            updateUser.setUf(userModel.getUf());

            userRepository.save(updateUser);
            
            if (userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(userModel.getPassword());
                updateUser.setPassword(encodedPassword);
            }



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
