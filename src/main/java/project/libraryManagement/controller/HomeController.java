package project.libraryManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String rootRedirect() {
        // 루트 URL 접근 시 권한에 따라 리다이렉트
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/home";
        }

        return "redirect:/main";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "home";
    }

    @GetMapping("/main")
    public String userHome() {
        return "main";
    }
}