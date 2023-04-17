package com.gunbro.gunvie.module;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileValidationCheck {

    //이미지 유효 검사
    //업로드 된 이미지가 유효한지, 깨진 파일은 아닌지 검사하는 메소드
    public boolean imageCheck(MultipartFile[] multipartFiles) throws IOException {
        //TODO IOExcepton 예외처리
        for (MultipartFile multipartFile : multipartFiles) {
            byte[] bytes = multipartFile.getBytes();
            try {
                InputStream inputStream = new ByteArrayInputStream(bytes);
                BufferedImage image = ImageIO.read(inputStream);
                if (image == null) {
                    return false;
                } else {
                    continue;
                }
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
