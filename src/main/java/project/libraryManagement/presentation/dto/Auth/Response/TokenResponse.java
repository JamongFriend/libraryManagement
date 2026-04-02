package project.libraryManagement.presentation.dto.Auth.Response;

import lombok.Builder;

@Builder
public record TokenResponse(
        Long memberId,
        String accessToken,
        String refreshToken
) {
}
