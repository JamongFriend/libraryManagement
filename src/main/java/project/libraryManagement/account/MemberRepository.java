package project.libraryManagement.account;

import java.awt.*;

public interface MemberRepository {
    void save(Member member);

    Member findById(String memberid);
}
