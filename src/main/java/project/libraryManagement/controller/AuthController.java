package project.libraryManagement.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 비우기
        return "redirect:/";
    }
}
