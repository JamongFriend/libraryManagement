package project.libraryManagement.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.libraryManagement.domain.Rental;
import project.libraryManagement.service.RentalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/rentals")
public class AppRentalController {
    private RentalService rentalService;

    @PostMapping(value = "/rentals")
    public String rentalBook(@RequestParam("memberId") Long memberId,
                             @RequestParam("bookId") Long bookId,
                             @RequestParam("count") int count,
                             @RequestParam("period") int period){
        rentalService.rentalBook(memberId, bookId, count, period);

        return "redirect:/rentals";
    }

    @GetMapping
    public String myRentals(@SessionAttribute("memberId") Long memberId, Model model) {
        List<Rental> rentals = rentalService.findByMemberId(memberId);
        model.addAttribute("rentals", rentals);
        return "app/rentals/myList";
    }

    @PostMapping("/rentals/return")
    public String returnBook(@SessionAttribute("memberId") Long memberId, @PathVariable Long rentalId) {
        rentalService.returnBook(rentalId, memberId);
        return "redirect:/rentals";
    }
}
