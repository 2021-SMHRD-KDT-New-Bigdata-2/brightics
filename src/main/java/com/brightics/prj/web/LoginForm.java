package com.brightics.prj.web;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter@Setter
public class LoginForm {

    @NotBlank
    private String loginId;

    @NotBlank

    private String password;
}
