package project.libraryManagement.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.libraryManagement.service.ReservationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/reservations")
@PreAuthorize("hasRole('ADMIN')")
public class AdminReservationController {
    private ReservationService reservationService;

    @GetMapping
    public String list(Model model){
        model.addAttribute("reservations", reservationService.findAll());
        return "admin/reservations/list";
    }

    @PostMapping("/{reservationId}/cancel")
    public String cancel(@PathVariable Long id){
        reservationService.cancel(id);
        return "rediract:/admin/reservations";
    }
}
