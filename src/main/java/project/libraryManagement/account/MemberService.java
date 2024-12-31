package project.libraryManagement.account;

import project.libraryManagement.domain.Member;

public interface MemberService {
    Member login(String memberId, String memberPw);
    void logout(String memberId);
    Member signUp(Member member);
    void destroyAccount(String memberId);
}
