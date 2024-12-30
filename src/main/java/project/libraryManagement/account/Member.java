package project.libraryManagement.account;

import lombok.Getter;

@Getter
public class Member {
    private String id;
    private String name;
    private String password;
    private String email;

    public Member(String id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
