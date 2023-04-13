package com.gunbro.gunvie.model.requestDto.User;

import com.gunbro.gunvie.config.enumData.EmailType;

public class SearchIdRequestDto {
    private String name;
    private String email;
    private EmailType emailType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }
}
