package com.brightics.prj.web.form;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordForm {


    @NotBlank
    @Length(min=6,max=20)
    private String password;

}
