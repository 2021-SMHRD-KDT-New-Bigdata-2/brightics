package com.brightics.prj.configure;

import com.brightics.prj.web.service.LoginFailHandler;
import com.brightics.prj.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private final DataSource dataSource;
    private final LoginFailHandler loginFailHandler;
    private final PasswordEncoder passwordEncoder;
    

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().antMatchers("/assets/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") //admin 권한이 있어야 /admin 경로 접속가능
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login") //login 설정
                .usernameParameter("loginId")
                .passwordParameter("password")
                .failureHandler(loginFailHandler)
                .permitAll();

        http.logout() //logout 설정
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        http.rememberMe() //rememberMe 설정
                .userDetailsService(memberService)
                .tokenRepository(tokenRepository());

        http.oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll();

        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);

    }


    @Bean //rememberMe의 토큰 관리
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository= new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}
