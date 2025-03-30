package com.thymeleaf.crud.thcrud.Controller;

import com.thymeleaf.crud.thcrud.Model.UserModel;
import com.thymeleaf.crud.thcrud.Repository.UserRepository;
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

    @PutMapping(value = "/update/{id}")
    public String UpdateUser(@PathVariable long id, @RequestBody UserModel userModel){
        UserModel updateUser = userRepository.findById(id).get();
        updateUser.setName(userModel.getName());
        updateUser.setPassword(userModel.getPassword());
        userRepository.save(updateUser);
        return "Updated...";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String DeleteUser(@PathVariable long id){
        UserModel deleteUser = userRepository.findById(id).get();
        userRepository.delete(deleteUser);
        return "Deleted user with id: "+id;
    }
}
