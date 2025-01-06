package project.libraryManagement.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.libraryManagement.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByMemberId(String memberId);
    List<Member> findByName(String name);

    @Query("SELECT m FROM Member m WHERE m.name LIKE %:name%")
    List<Member> searchByName(@Param("name") String name);

}
