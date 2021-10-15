package com.brightics.prj.web.util;

import com.brightics.prj.web.form.SignupForm;
import com.brightics.prj.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class SignupFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignupForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignupForm signupForm=(SignupForm) target;
        if(memberRepository.existsMemberByLoginId(signupForm.getLoginId())){
            errors.rejectValue("loginId", "invalid.loginId",new Object[]{signupForm.getLoginId()}, "이미 존재하는 아이디입니다");
        }
        if(memberRepository.existsMemberByEmail(signupForm.getEmail())){
            errors.rejectValue("email", "invalid.email",new Object[]{signupForm.getEmail()}, "이미 존재하는 이메일입니다");
        }

    }
}
