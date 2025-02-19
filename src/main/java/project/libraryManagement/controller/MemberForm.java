package project.libraryManagement.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "이름을 입력해 주세요")
    private String name;

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력값입니다.")
    private String passwordConfirm;
}