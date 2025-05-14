package project.libraryManagement.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.libraryManagement.domain.Book;
import project.libraryManagement.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books/new")
    public String createForm(HttpSession session, Model model) {
        if (session.getAttribute("memberId") == null) {
            return "redirect:/login";
        }
        model.addAttribute("bookForm", new BookForm());
        return "books/createBook";
    }

    @PostMapping(value = "/books/new")
    public String create(BookForm form){
        Book book = new Book();
        form.setName(form.getName());
        form.setAuthor(form.getAuthor());
        form.setIsbn(form.getIsbn());
        form.setCategory(form.getCategory());
        form.setStockQuantity(form.getStockQuantity());
        return "redirect:/";
    }

    @GetMapping(value = "/books")
    public String list(Model model){
        List<Book> books = bookService.findAllBook();
        model.addAttribute("books", books);
        return "books/bookList";
    }

    @GetMapping(value = "/books/{bookId}/edit")
    public String updateBookForm(@PathVariable("itemId") Long id, Model model){
        Book book = bookService.findOne(id);
        BookForm form = new BookForm();
        form.setId(form.getId());
        form.setName(form.getName());
        form.setAuthor(form.getAuthor());
        form.setIsbn(form.getIsbn());
        form.setCategory(form.getCategory());
        form.setStockQuantity(form.getStockQuantity());
        model.addAttribute("form", form);
        return "books/updateBookForm";
    }

    @PostMapping(value = "/books/{bookId}/edit")
    public String updateBook(@PathVariable Long itemId, @ModelAttribute("form") BookForm form){
        bookService.updateBook(itemId, form.getName(), form.getIsbn(), form.getAuthor(), form.getCategory(), form.getStockQuantity());
        return "redirect:/books";
    }

    @DeleteMapping(value = "{bookId}")
    public String delete(@PathVariable("itemId") Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/books";
    }
}
