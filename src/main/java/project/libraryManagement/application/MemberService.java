package project.libraryManagement.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.libraryManagement.domain.member.Member;
import project.libraryManagement.infrastructure.member.MemberJpaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberJpaRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Long signUp(Member member) {
        validateDuplicateMember(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        Optional<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if(findMembers.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

    }

    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    public Member findOneMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteMember(id);
    }
}
