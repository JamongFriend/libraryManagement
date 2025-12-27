package project.libraryManagement.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.libraryManagement.controller.MemberForm;
import project.libraryManagement.domain.Member;
import project.libraryManagement.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/members")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMemberController {
    private final MemberService memberService;

    @GetMapping
    public String list(Model model){
        model.addAttribute("members", memberService.findAllMember());
        return "admin/members/list";
    }

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "admin/members/createForm";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("memberForm") MemberForm form,
                         BindingResult result) {
        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            result.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호가 일치하지 않습니다.");
        }
        if (result.hasErrors()) {
            return "admin/members/createForm";
        }
        Member member = new Member(form.getName(), form.getPassword(), form.getEmail());
        memberService.signUp(member);

        return "redirect:/admin/members";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        memberService.deleteMember(id);
        return "redirect:/admin/members";
    }
}
