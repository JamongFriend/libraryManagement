package project.libraryManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.libraryManagement.domain.Book;
import project.libraryManagement.domain.Member;
import project.libraryManagement.domain.Reservation;
import project.libraryManagement.service.BookService;
import project.libraryManagement.service.MemberService;
import project.libraryManagement.service.ReservationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final MemberService memberService;
    private final BookService bookService;

    @GetMapping(value = "/reservations")
    public String createForm(Model model,
                             @RequestParam("memberId") Long memberId){
        Member member = memberService.findOneMember(memberId);
        List<Book> books = bookService.findAllBook();

        model.addAttribute("members", member);
        model.addAttribute("books", books);

        return "reservations/createReservationForm";
    }

    @PostMapping(value = "/reservations")
    public String reservationBook(@RequestParam("memberId") Long memberId,
                                  @RequestParam("bookId") Long bookId,
                                  @RequestParam("count") int count) {
        reservationService.reservationBook(memberId, bookId, count);

        return "redirect:/reservations";
    }

    @PostMapping(value = "/reservations")
    public String cancelReservation(Long reservationId){
        reservationService.cancel(reservationId);

        return "redirect:/reservations";
    }

    @GetMapping(value = "/reservations")
    public String reservationList(Model model) {
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);

        return "redirect:/reservations";
    }
}
