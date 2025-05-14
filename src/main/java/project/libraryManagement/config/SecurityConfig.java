package project.libraryManagement.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers(
                                "/", "/login", "/logout", "/members/**",
                                "/css/**", "/js/**", "/images/**", "/books/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // 커스텀 로그인 페이지
                // 로그인 성공 시 홈으로 이동
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                // POST 방식 로그아웃 경로
                // 로그아웃 성공 시 홈으로 이동
                // 세션 초기화
                // 쿠키 제거 (선택)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                ).csrf(config -> config.disable());

        return http.build();
    }
}
