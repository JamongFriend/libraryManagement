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

    public List<ApiBookDto> searchBooks(String keyword, int pageNum) {
        String url = "https://www.nl.go.kr/NL/search/openApi/search.do"
                + "?key=" + apiKey
                + "&kwd=" + keyword
                + "&pageSize=10&pageNum=" + pageNum
                + "&format=json";  // JSON 형식 명시
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            JsonNode docs = root.path("result").path("docs");
            List<ApiBookDto> result = new ArrayList<>();

            for(JsonNode doc : docs) {
                ApiBookDto dto = objectMapper.treeToValue(doc, ApiBookDto.class);
                result.add(dto);
            }
            return result;
        } catch (Exception e) {
            log.error("국립중앙도서관 API 호출 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("API 응답 처리 중 오류 발생", e);
        }
    }
}
