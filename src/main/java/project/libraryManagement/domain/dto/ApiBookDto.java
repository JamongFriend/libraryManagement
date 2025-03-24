package project.libraryManagement.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiBookDto {
    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("isbn")
    private String isbn;

    

    @JsonProperty("publication_year")
    private String publicationYear;
}
