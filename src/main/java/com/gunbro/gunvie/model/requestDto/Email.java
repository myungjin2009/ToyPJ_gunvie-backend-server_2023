package com.gunbro.gunvie.model.requestDto;

import com.gunbro.gunvie.config.enumData.EmailType;

import java.time.LocalDateTime;

public class Email {

    private String email;

    private EmailType emailType;

    private String verifyNumber;

    private LocalDateTime mailSendDate;

    private boolean verified = false;




    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

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

    public String getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(String verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    public LocalDateTime getMailSendDate() {
        return mailSendDate;
    }

    public void setMailSendDate(LocalDateTime mailSendDate) {
        this.mailSendDate = mailSendDate;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
