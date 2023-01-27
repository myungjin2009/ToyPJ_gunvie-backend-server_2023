package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.config.enumData.EmailType;
import com.gunbro.gunvie.model.requestDto.Email;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import com.gunbro.gunvie.service.EmailService;
import com.gunbro.gunvie.service.VerifyService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.sqm.function.JdbcEscapeFunctionDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    VerifyService verifyService;

    @PostMapping("/send")
    public DefaultDto sendEmail(@RequestBody Email email, HttpSession httpSession) {
        EmailType type = email.getEmailType();
        DefaultDto dto;
        switch(type) {
            case VERIFY_NUMBER -> {
                //TODO 이메일 인증 : 난수발생기 모듈 만들기
                String verifyNumber = "000000";
                dto = emailService.sendMailVerifyNumber(email,verifyNumber);
                if(dto.getCode() == 200) {
                    email.setVerifyNumber(verifyNumber);
                    email.setMailSendDate(LocalDateTime.now());
                    httpSession.setAttribute("emailVerify", email);
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
        Email existEmail = (Email) httpSession.getAttribute("emailVerify");

        boolean isNotExpired = verifyService.checkExpireDate(existEmail);
        if(!isNotExpired) {
            defaultDto.setCode(403);
            defaultDto.setMessage("세션이 지워졌거나 너무 오래되었습니다.");
        } else {
            boolean isMatch = verifyService.verifyEqual(receivedEmail, existEmail);
            if(!isMatch) {
                defaultDto.setCode(403);
                defaultDto.setMessage("인증번호가 불일치 합니다.");
            } else {
                defaultDto.setCode(200);
                defaultDto.setMessage("인증번호가 일치 합니다.");
            }
        }
        return defaultDto;
    }
}
