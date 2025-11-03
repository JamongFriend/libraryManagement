package project.libraryManagement.controller.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.libraryManagement.controller.BookForm;
import project.libraryManagement.domain.Book;
import project.libraryManagement.domain.Category;
import project.libraryManagement.service.BookService;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class AdminBookController {
    private BookService bookService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAllBook());
        return "bookList";
    }

    @GetMapping("/new")
    public String createForm(HttpSession session, Model model) {
        if (session.getAttribute("memberId") == null) {
            return "redirect:/login";
        }
        model.addAttribute("bookForm", new BookForm());
        model.addAttribute("categories", Category.values());
        return "createBook";
    }

    @PostMapping
    public String create(BookForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "createBook";
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
        return "redirect:/admin/books";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("categories", Category.values());
        return "updateBookForm";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, BookForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "updateBookForm";
        }
        bookService.updateBook(id, form.getTitle()
                , form.getIsbn(), form.getPublisher(), form.getPublicationYear(),
                form.getAuthor(), form.getCategory(), form.getStockQuantity()
        );
        return "redirect:/admin/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }
}
