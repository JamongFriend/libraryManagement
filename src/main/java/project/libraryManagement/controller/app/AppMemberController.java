package project.libraryManagement.controller.app;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.libraryManagement.controller.MemberForm;
import project.libraryManagement.domain.Member;
import project.libraryManagement.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/members")
public class AppMemberController {
    private final MemberService memberService;

    @GetMapping
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "admin/members/createForm";
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

    @GetMapping("/my-page")
    public String myPage(@SessionAttribute("memberId") Long memberId, Model model) {
        Member member = memberService.findOneMember(memberId);
        model.addAttribute("member", member);
        return "app/members/myPage";
    }
}
