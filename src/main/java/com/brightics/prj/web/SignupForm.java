package com.brightics.prj.web;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignupForm {

    @NotBlank
    @Length(min=4, max=12)
    @Pattern(regexp = "^[a-zA-Z0-9_-]{4,12}$")
    private String loginId;

    @NotBlank
    @Length(min=6, max=20)
    private String password;

    @Email
    @NotBlank
    private String email;

}
