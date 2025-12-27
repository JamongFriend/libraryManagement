package project.libraryManagement.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.libraryManagement.service.RentalService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/rentals")
public class AdminRentalController {
    private final RentalService rentalService;

    @GetMapping
    public String list(@RequestParam(required = false) String status, Model model){
        model.addAttribute("rentals", rentalService.findAll(status));
        return "admin/rentals/list";
    }

    // 관리자 강제 반납(연체/분실 처리 등)
    @PostMapping("/{rentalId}/force-return")
    public String forceReturn(@PathVariable Long rentalId) {
        rentalService.forceReturn(rentalId);
        return "redirect:/admin/rentals";
    }

    // 예약 대기자 자동 할당 수동 트리거(옵션)
    @PostMapping("/books/{bookId}/allocate-next")
    public String allocateNext(@PathVariable Long bookId) {
        rentalService.allocateToNextReservationIfAny(bookId);
        return "redirect:/admin/rentals";
    }
}
