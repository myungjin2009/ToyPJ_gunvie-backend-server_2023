package com.gunbro.gunvie.service;

import com.gunbro.gunvie.model.requestDto.Email;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();



    public DefaultDto sendMailVerifyNumber(Email email, String verifyNumber) {
        String[] receiveList = {email.getEmail()};
        String title = "Gunvie 인증번호 안내입니다.";
        String content = "메일 인증번호는 "+ verifyNumber + " 입니다.";

        //String result = sendMailFinal(receiveList, title, content);
        String result = "successful";

        DefaultDto dto = new DefaultDto();

        if(!result.equals("successful")) {
            dto.setCode(500);
            dto.setMessage(result);
        } else {
            dto.setCode(200);
            dto.setMessage("Mail sent successfully");
        }
        return dto;
    }

    public String sendMailFinal(String[] receiveList, String title, String content) {
        String result = "successful";
        try {
            //1. 메일 수신자 설정
            simpleMailMessage.setTo(receiveList);

            //2. 메일 제목 설정
            simpleMailMessage.setSubject(title);

            //3. 메일 내용 설정
            simpleMailMessage.setText(content);

            //4. 메일 전송
            javaMailSender.send(simpleMailMessage);
        }catch(Exception e) {
            result = e.getMessage();
            e.printStackTrace();
        }finally {
            return result;
        }
    }

}
