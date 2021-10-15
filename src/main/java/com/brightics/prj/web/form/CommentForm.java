package com.brightics.prj.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {
    @NotBlank
    private String comment;
}
