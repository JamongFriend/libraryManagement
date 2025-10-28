package project.libraryManagement.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.libraryManagement.domain.Book;
import project.libraryManagement.domain.Category;
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
        model.addAttribute("categories", Category.values());
        return "books/createBook";
    }

    @PostMapping(value = "/books/new")
    public String create(BookForm form, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "books/createBook";
        }
        Book book = new Book(
                form.getTitle(),
                form.getStockQuantity(),
                form.getAuthor(),
                form.getIsbn(),
                form.getPublisher(),
                form.getPublicationYear(),
                form.getThumbnailUrl(),
                form.getCategory()
        );
        bookService.saveBook(book);
        return "redirect:/";
    }

    @GetMapping(value = "/books")
    public String list(HttpSession session, Model model){
        List<Book> books = bookService.findAllBook();
        model.addAttribute("books", books);
        model.addAttribute("isLoggedIn", session.getAttribute("memberId") != null);

        return "books/bookList";
    }

    @GetMapping(value = "/books/{bookId}/edit")
    public String updateBookForm(@PathVariable("bookId") Long id, Model model){
        Book book = bookService.findOne(id);
        model.addAttribute("form", BookForm.of(book));
        model.addAttribute("categories", Category.values());
        return "books/updateBookForm";
    }

    @PostMapping(value = "/books/{bookId}/edit")
    public String updateBook(@PathVariable("bookId") Long bookId, @ModelAttribute("form") BookForm form){
        bookService.updateBook(bookId, form.getTitle()
                , form.getIsbn(), form.getPublisher(), form.getPublicationYear(),
                form.getAuthor(), form.getCategory(), form.getStockQuantity());
        System.out.println("✅ 수정 실행됨: " + bookId + " / " + form.getTitle());
        return "redirect:/books";
    }

    @DeleteMapping(value = "/books/{bookId}")
    public String delete(@PathVariable("bookId") Long bookId, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(bookId);
            redirectAttributes.addFlashAttribute("message", "도서가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/books";
    }
}
