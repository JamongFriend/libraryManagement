package project.libraryManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.libraryManagement.domain.dto.ApiBookDto;
import project.libraryManagement.service.LibraryApiService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookApiController {

    private final LibraryApiService libraryApiService;

    public BookApiController(LibraryApiService libraryApiService) {
        this.libraryApiService = libraryApiService;
    }

    @GetMapping("/search")
    public List<ApiBookDto> searchBooks(@RequestParam String keyword, @RequestParam int page) {
        return libraryApiService.searchBooks(keyword, page);
    }
}

