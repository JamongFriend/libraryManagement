package project.libraryManagement.infrastructure.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.auth.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findTopByMemberIdAndRevokedFalseOrderByIdDesc(Long memberId);

    RefreshToken save(RefreshToken token);

    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.memberId = :memberId")
    void deleteByMemberId(@Param("memberId") Long memberId);
}
