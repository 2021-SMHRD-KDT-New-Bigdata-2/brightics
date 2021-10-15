package com.brightics.prj.web.form;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ForgotPasswordForm {

    @NotBlank
    String loginId;

}
