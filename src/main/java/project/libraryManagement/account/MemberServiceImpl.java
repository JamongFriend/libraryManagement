package project.libraryManagement.account;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.libraryManagement.domain.Member;

@Service
@Getter
public class MemberServiceImpl implements MemberService{
    private final MemberService memberService;

    @Autowired
    public MemberServiceImpl(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public Member login(String memberId, String memberPw) {
        return null;
    }

    @Override
    public void logout(String memberId) {

    }

    @Override
    public Member signUp(Member member) {
        return null;
    }

    @Override
    public void destroyAccount(String memberId) {

    }
}
