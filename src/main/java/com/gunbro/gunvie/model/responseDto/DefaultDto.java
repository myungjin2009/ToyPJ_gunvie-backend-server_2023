package com.gunbro.gunvie.model.responseDto;

import java.util.HashMap;
import java.util.Map;

public class DefaultDto {

    private int code;

    private String message;


    public DefaultDto() {
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
