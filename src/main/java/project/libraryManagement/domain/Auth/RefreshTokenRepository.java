package project.libraryManagement.domain.Auth;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository {
    Optional<RefreshToken> findValidByMemberId(Long memberId);

    RefreshToken save(RefreshToken token);

    void deleteByMemberId(Long memberId);
}
