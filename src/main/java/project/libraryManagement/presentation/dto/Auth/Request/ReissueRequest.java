package project.libraryManagement.presentation.dto.Auth.Request;

import jakarta.validation.constraints.NotBlank;

public record ReissueRequest(
        @NotBlank String refreshToken
) {
}
