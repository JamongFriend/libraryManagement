package project.libraryManagement.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import project.libraryManagement.application.MemberService;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

//    @PostMapping("/login")
//    public String login(@RequestParam String email,
//                        @RequestParam String password,
//                        HttpSession session,
//                        Model model) {
//        try {
//            Long memberId = memberService.login(email, password, session);
//            session.setAttribute("memberId", memberId);
//
//            UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(email, null, List.of());
//            SecurityContextHolder.getContext().setAuthentication(auth);
//
//            return "redirect:/";
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("loginError", e.getMessage());
//            return "login";
//        }
//    }
}

