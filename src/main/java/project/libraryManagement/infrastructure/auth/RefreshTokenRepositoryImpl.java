package project.libraryManagement.infrastructure.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.auth.RefreshToken;
import project.libraryManagement.domain.auth.RefreshTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository repository;
    @Override
    public Optional<RefreshToken> findValidByMemberId(Long memberId) {
        return repository.findTopByMemberIdAndRevokedFalseOrderByIdDesc(memberId)
                .filter(rt -> rt.getExpiresAt().isAfter(LocalDateTime.now()));
    }

    @Override
    public RefreshToken save(RefreshToken token) {
        return repository.save(token);
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        repository.deleteByMemberId(memberId);
    }
}
