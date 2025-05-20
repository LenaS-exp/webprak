package ru.msu.cmc.webprak.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webprak.model.dao.PassangersDAO;
import ru.msu.cmc.webprak.model.entity.Passangers;

import java.util.Collection;

@Controller
public class RegistrationController {

    @Autowired
    private PassangersDAO usersDAO;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            Model model) {

        Collection<Passangers> existingUser = usersDAO.getPassangersByFilter(PassangersDAO.getFilterBuilder().email(email).build());

        if (!existingUser.isEmpty()) {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            return "register";
        }

        Passangers newUser = new Passangers();
        newUser.setName(firstName);
        newUser.setSurname(lastName);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phone);
        newUser.setPassword(password);

        try {
            usersDAO.add(newUser);
            model.addAttribute("success", "Регистрация прошла успешно!");
            return "redirect:/flights";
        } catch (Exception e) {
            return "redirect:/register";
        }

    }
}