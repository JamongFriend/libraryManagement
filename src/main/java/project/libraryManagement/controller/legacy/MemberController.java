package project.libraryManagement.controller.legacy;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.libraryManagement.controller.MemberForm;
import project.libraryManagement.domain.Member;
import project.libraryManagement.service.MemberService;

@Profile("legacy")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "member/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if(!form.getPassword().equals(form.getPasswordConfirm())){
            result.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호가 일치하지 않습니다.");
        }

        if(result.hasErrors()){
            return "member/createMemberForm";
        }

        Member member = new Member(form.getName(), form.getPassword(), form.getEmail());
        memberService.signUp(member);

        return "redirect:/";
    }
}
