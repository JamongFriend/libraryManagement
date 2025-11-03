package project.libraryManagement.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.libraryManagement.domain.dto.ApiBookDto;
import project.libraryManagement.service.BookService;
import project.libraryManagement.service.LibraryApiService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/books")
@PreAuthorize("hasRole('ADMIN')")
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

    private String normalizeIsbn(String raw) {
        return raw.replaceAll("[^0-9Xx]", "").toUpperCase();
    }

    private static void validateIsbn(String isbn) {
        if (!(isbn.length() == 10 || isbn.length() == 13)) {
            throw new IllegalArgumentException("ISBN 형식이 올바르지 않습니다.");
        }
        // 필요하면 체크디지트 검증까지 추가 가능
    }
}

