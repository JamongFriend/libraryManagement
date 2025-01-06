package project.libraryManagement.account;

import org.springframework.stereotype.Service;
import project.libraryManagement.domain.Member;

import java.util.List;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member login(String memberId, String memberPw) {
        Member member = memberRepository.findByMemberId(memberId);
        if(member != null && member.getPassword().equals(memberPw)){
            return member;
        }
        else{
            throw new IllegalArgumentException("회원정보를 찾을 수 없습니다.");
        }
    }

    public void logout(String memberId) {
    }

    public Long signUp(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void destroyAccount(String memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        if(memberId != null)
            memberRepository.delete(member);
        else
            throw new IllegalArgumentException("회원정보를 찾을 수 없습니다.");

    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }
}
