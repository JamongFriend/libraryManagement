package project.libraryManagement.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.libraryManagement.domain.Member;
import project.libraryManagement.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
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

    public Long login(String email, String password, HttpSession session) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute("member_id", member.getId());

        return member.getId();
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
