package ru.nurislam.springcourse.movieprojectmain.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nurislam.springcourse.movieprojectmain.service.AiService;

import java.security.Principal;

@Controller
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    // Открыть чат-страницу
    @GetMapping
    public String chatPage() {
        return "ai-chat";
    }

    @PostMapping("/ask")
    public String ask(@RequestParam String message,
                      Principal principal,
                      Model model) {

        // 1. проверка сообщения
        if (message == null || message.trim().isEmpty()) {
            model.addAttribute("answer", "Введите вопрос");
            return "ai-chat";
        }

        // 2. безопасный user
        String user = (principal != null)
                ? principal.getName()
                : "guest";

        // 3. AI ответ
        String answer = aiService.ask(user, message);

        // 4. передача в UI
        model.addAttribute("question", message);
        model.addAttribute("answer", answer);

        return "ai-chat";
    }
}