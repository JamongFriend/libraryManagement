package project.libraryManagement.presentation.dto.Auth.Request;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank String refreshToken
) {
}
