package project.libraryManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.libraryManagement.domain.dto.ApiBookDto;
import project.libraryManagement.service.BookService;
import project.libraryManagement.service.LibraryApiService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookApiController {

    private final LibraryApiService libraryApiService;
    private final BookService bookService;

    @GetMapping("/lookup-isbn")
    public ResponseEntity<ApiBookDto>  lookupByIsbn(@RequestParam String isbn) {
        String normalized = normalizeIsbn(isbn);
        validateIsbn(normalized);
        ApiBookDto dto = libraryApiService.fetchByIsbn(normalized);
        if (dto == null) {
            // 외부에 정보가 없을 때
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    // 2) 즉시 등록/증가
    @PostMapping(value = "/register-by-isbn", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Long> registerByIsbn(@RequestParam String isbn,
                                               @RequestParam(defaultValue = "1") int stock) {
        String normalized = normalizeIsbn(isbn);
        validateIsbn(normalized);
        if (stock <= 0) throw new IllegalArgumentException("stock은 1 이상이어야 합니다.");

        Long id = bookService.upsertByIsbn(normalized, stock);
        return ResponseEntity
                .created(URI.create("/api/books/" + id))
                .body(id);
    }


    private static String normalizeIsbn(String raw) {
        if(raw == null) return null;
        return raw.replaceAll("[^0-9Xx]", "").toUpperCase();
    }

    private static void validateIsbn(String isbn) {
        if (isbn == null || !(isbn.length() == 10 || isbn.length() == 13)) {
            throw new IllegalArgumentException("유효하지 않은 ISBN 형식입니다. (10자리 또는 13자리)");
        }
        // 필요하면 체크디지트 검증까지 추가 가능
    }
}

