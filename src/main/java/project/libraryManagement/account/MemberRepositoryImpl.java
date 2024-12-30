package project.libraryManagement.account;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class MemberRepositoryImpl implements MemberRepository{
    private final MemberRepository memberRepository;

    @Autowired
    public MemberRepositoryImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void save(Member member){
        memberRepository.save(member);
    }

    @Override
    public Member findById(String memberId){
        return memberRepository.findById(memberId);
    }
}
