package project.libraryManagement.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import project.libraryManagement.security.MemberDetails;

import java.io.IOException;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // MemberDetails에서 memberId 꺼내기
        MemberDetails userDetails = (MemberDetails) authentication.getPrincipal();
        Long memberId = userDetails.getId();

        // 세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("memberId", memberId);

        // 원하는 URL로 리다이렉트
        response.sendRedirect("/");
    }
}
