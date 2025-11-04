package project.libraryManagement.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.libraryManagement.controller.MemberForm;
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

    @GetMapping
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "admin/members/createForm";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        memberService.deleteMember(id);
        return "redirect:/admin/members";
    }
}
