package com.gunbro.gunvie.model.responseDto.Post;

import com.gunbro.gunvie.model.responseDto.DefaultDto;

public class AddImageResponseDto extends DefaultDto {
    private String fileName;

    public String getFilePath() {
        return fileName;
    }

    public void setFilePath(String fileName) {
        this.fileName = fileName;
    }
}
