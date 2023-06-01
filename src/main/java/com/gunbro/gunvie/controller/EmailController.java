package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.config.enumData.EmailType;
import com.gunbro.gunvie.model.requestDto.Email;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import com.gunbro.gunvie.module.randomNumber.RandomNumberGenerator;
import com.gunbro.gunvie.service.EmailService;
import com.gunbro.gunvie.service.VerifyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final VerifyService verifyService;
    private final RandomNumberGenerator randomNumberGenerator;
    @Value("${mail.verification.digit}")
    private int verificationDigit;

    @PostMapping("/send")
    public DefaultDto sendEmail(@RequestBody Email email, HttpSession httpSession) {
        DefaultDto dto;
        EmailType type = email.getEmailType();
        switch(type) {
            case VERIFY_NUMBER,FIND_ID,FIND_PW -> {
                String verifyNumber = randomNumberGenerator.generate(verificationDigit);
                dto = emailService.sendMailVerifyNumber(email,verifyNumber);
                if(dto.getCode() == 200) {
                    email.setVerifyNumber(verifyNumber);
                    email.setMailSendDate(LocalDateTime.now());
                    httpSession.setAttribute("emailVerify"+type.name(), email);
                    httpSession.setMaxInactiveInterval(180); //180초
                }
                return dto;
            }
            case ADVERTISEMENT -> {
                return null;
            }
            default -> {
                //타입오류는 여기에서 처리되지 않음. enum에서 걸러져서
                //HttpMessageNotReadableException, 400 Bad Request오류 발생
                //TODO 오류처리 : 잘못된 이메일 타입 받을 시 커스텀 오류 처리 하기
                return null;
            }
        }
    }

    @PostMapping("/verify")
    public DefaultDto verifyEmail(@RequestBody Email receivedEmail, HttpSession httpSession) {
        DefaultDto defaultDto = new DefaultDto();
        EmailType type = receivedEmail.getEmailType();
        Email existEmail = (Email) httpSession.getAttribute("emailVerify"+type.name());

        boolean isNotExpired = verifyService.checkExpireDate(existEmail);
        if(!isNotExpired) {
            defaultDto.setCode(403);
            defaultDto.setMessage("세션이 지워졌거나 너무 오래되었습니다.");
        } else {
            boolean isMatch = verifyService.verifyEqual(receivedEmail, existEmail);
            if(!isMatch) {
                defaultDto.setCode(403);
                defaultDto.setMessage("이메일/인증번호가 불일치 합니다.");
            } else {
                defaultDto.setCode(200);
                defaultDto.setMessage("인증번호가 일치 합니다.");
                existEmail.setVerified(true);
                httpSession.setAttribute("emailVerify"+type.name(), existEmail);
            }
        }
        return defaultDto;
    }
}
