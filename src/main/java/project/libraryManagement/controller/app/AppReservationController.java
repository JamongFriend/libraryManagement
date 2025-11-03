package project.libraryManagement.controller.app;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.libraryManagement.domain.Reservation;
import project.libraryManagement.service.ReservationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class AppReservationController {
    private final ReservationService reservationService;

    @PostMapping("/app/reservations")
    public String createReservation(@RequestParam Long bookId, @RequestParam int count, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        reservationService.reservationBook(memberId, bookId, count);
        return "redirect:/app/me/reservations";
    }

    @PostMapping("/app/reservations/{reservationId}/cancel")
    public String cancelReservation(@PathVariable Long reservationId,
                                    HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        // 서비스에서 memberId와 reservationId 일치 여부 검증하도록 구현 권장
        reservationService.cancelByOwner(memberId, reservationId);
        return "redirect:/app/me/reservations";
    }

    @GetMapping("/app/me/reservations")
    public String myReservations(Model model, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) return "redirect:/login";

        List<Reservation> reservations = reservationService.findByMemberId(memberId);
        model.addAttribute("reservations", reservations);
        return "app/me/reservations"; // (없으면 템플릿만 하나 만들어두면 OK)
    }
}
