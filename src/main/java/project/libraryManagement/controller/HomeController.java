package project.libraryManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    // 루트 URL 접근 시 권한에 따라 리다이렉트
    @GetMapping("/")
    public String rootRedirect() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/admin-home";
        }

        return "redirect:/main";
    }

    @GetMapping("/admin/admin-home")
    public String adminHome() {
        return "admin-home";
    }

    @GetMapping("app/app-home")
    public String userHome() {
        return "app-home";
    }
}