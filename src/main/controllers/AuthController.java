package ru.nurislam.springcourse.movieprojectmain.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nurislam.springcourse.movieprojectmain.entities.User;
import ru.nurislam.springcourse.movieprojectmain.repository.UserRepository;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userRepository.findByLogin(login).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            System.out.println("LOGIN OK: " + user.getLogin()); // 🔥 DEBUG
            return "redirect:/movies";
        }

        model.addAttribute("error", "Wrong login or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}