package project.libraryManagement.infrastructure.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.member.Member;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

}
