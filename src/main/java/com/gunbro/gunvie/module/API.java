package com.gunbro.gunvie.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.image.Kernel;
import java.util.List;
import java.util.Map;

public class API {

    //TODO : targetDt 유동값으로 수정 필요
    public String getDataAPI(String applicationType, String url, Map<String, String> queryParam) {
        //TODO : RestTemplateBuilder 에 대한 이해 필요..
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application",applicationType));

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        if(!queryParam.isEmpty()) {
            queryParam.entrySet().stream()
                    .forEach(data -> {
                        uriBuilder.queryParam(data.getKey(), data.getValue());
                    });
        }


        ResponseEntity<String> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        return response.getBody();
    }
}
