package project.libraryManagement.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.libraryManagement.service.BookService;

@Controller
@RequestMapping("/app/books")
@RequiredArgsConstructor
public class AppBookController {

    private final BookService bookService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAllBook());
        return "bookList";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        return "bookDetail";
    }
}