package project.libraryManagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.libraryManagement.domain.dto.ApiBookDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryApiService {
    @Value("${library.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ApiBookDto> searchBooks(String keyword, int pageNum) {
        String url = "https://www.nl.go.kr/NL/search/openApi/search.do"
                + "?key=" + apiKey
                + "&kwd=" + keyword
                + "&pageSize=10&pageNum=" + pageNum
                + "&format=json";  // JSON 형식 명시

        String response = restTemplate.getForObject(url, String.class);

        try {
            // API의 JSON 구조에 맞게 파싱 로직을 조정해야 함.
            return objectMapper.readValue(response,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ApiBookDto.class));
        } catch (Exception e) {
            throw new RuntimeException("API 응답 처리 중 오류 발생", e);
        }
    }
}
