package com.gunbro.gunvie.module;

import com.gunbro.gunvie.config.enumData.FileType;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileCheck {

    //이미지 유효 검사
    //업로드 된 이미지가 유효한지, 깨진 파일은 아닌지 검사하는 메소드
    public boolean imageCheck(MultipartFile multipartFile) throws IOException {
        //TODO IOExcepton 예외처리
        byte[] bytes = multipartFile.getBytes();
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    //파일명 중복 방지
    //기존 파일명 앞에 UUID 를 붙여 반환하는 메소드
    public String convertUniqueName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        return uuid+"_"+originalFileName;
    }

    //파일 저장
    public String saveFile(MultipartFile multipartFile, String fileName, FileType fileType) {
        String path = System.getProperty("user.dir") + File.separator
                                        + "src" + File.separator
                                        + "main" + File.separator
                                        + "resources" + File.separator;

        if(fileType == FileType.POSTIMAGE) {
            path += "post_image" + File.separator + fileName;
        }

        File file = new File(path);
        if (!file.exists()) file.mkdirs(); //TODO 성능에 영향을 주지 않을까? 스프링부트 시작시에 한 번만 체크 하면 될 것 같은데..
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            return null;
        }
        return path;
    }
}
