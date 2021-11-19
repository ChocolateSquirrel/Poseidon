package com.nnk.springboot.commandobject;

import lombok.Data;

@Data
public class LogedForm {

    private String username;
    private String password;

    public LogedForm() {
    }
}
