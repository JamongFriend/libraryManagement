package project.libraryManagement.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MemberRepository memberRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public Long signUp(Member member, String name, String email, String password) {
        validateDuplicateMember(member);
        String encodeedPassword = passwordEncoder.encode(password);
        Member newMember = new Member(name, email, encodeedPassword);
        memberRepository.save(newMember);
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
}
