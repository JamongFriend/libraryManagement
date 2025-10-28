package project.libraryManagement.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.libraryManagement.domain.dto.ApiBookDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryApiService {
    @Value("${library.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ApiBookDto fetchByIsbn(String isbn) {
        String url = "https://www.nl.go.kr/NL/search/openApi/search.do"
                + "?key=" + apiKey
                + "&kwd=" + isbn
                + "&pageSize=10&pageNum=1"
                + "&format=json";
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            JsonNode docs = root.path("result").path("docs");
            if(!docs.isArray() || docs.size() == 0) return null;
            JsonNode doc = docs.get(0);

            ApiBookDto dto = new ApiBookDto();
            dto.setTitle(doc.path("titleInfo").asText(null));
            dto.setAuthor(doc.path("authorInfo").asText(null));
            dto.setPublisher(doc.path("publisherInfo").asText(null));
            dto.setPublicationYear(doc.path("publicationYearInfo").asText(null));
            dto.setIsbn(doc.path("isbnInfo").asText(isbn));
            dto.setThumbnailUrl(doc.path("thumbnailUrlInfo").asText(null));

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("국립중앙도서관 ISBN 조회 실패", e);
        }
    }
}
