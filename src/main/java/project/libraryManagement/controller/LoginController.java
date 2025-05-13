package project.libraryManagement.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.libraryManagement.service.MemberService;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            Long memberId = memberService.login(email, password, session);
            session.setAttribute("memberId", memberId);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("loginError", e.getMessage());
            return "login";
        }
    }
}

