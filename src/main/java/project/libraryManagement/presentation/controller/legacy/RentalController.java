package project.libraryManagement.presentation.controller.legacy;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.libraryManagement.domain.book.Book;
import project.libraryManagement.domain.member.Member;
import project.libraryManagement.domain.rental.Rental;
import project.libraryManagement.application.BookService;
import project.libraryManagement.application.MemberService;
import project.libraryManagement.application.RentalService;

import java.util.List;

@Profile("legacy")
@Controller
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;
    private final MemberService memberService;
    private final BookService bookService;

    @GetMapping(value = "/rentals")
    public String createForm(Model model) {
        List<Member> members = memberService.findAllMember();
        List<Book> books = bookService.findAllBook();

        model.addAttribute("members", members);
        model.addAttribute("books", books);

        return "rentals/createRentalForm";
    }

    @PostMapping(value = "/rentals")
    public String rentalBook(@RequestParam("memberId") Long memberId,
                             @RequestParam("bookId") Long bookId,
                             @RequestParam("count") int count,
                             @RequestParam("period") int period){
        rentalService.rentalBook(memberId, bookId, count, period);

        return "redirect:/rentals";
    }

    @PostMapping("/rentals/return")
    public String returnBook(@RequestParam Long rentalId,
                             @RequestParam("memberId") Long memberId) {
        rentalService.returnBook(rentalId, memberId);
        return "redirect:/rentals";
    }

    @GetMapping(value = "/rentals/list")
    public String list(Model model,
                       @RequestParam String status){
        List<Rental> rentals = rentalService.findAll(status);
        model.addAttribute("rentals", rentals);
        return "rentals/rentalList";
    }
}
