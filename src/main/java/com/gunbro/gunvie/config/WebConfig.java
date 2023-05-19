package com.gunbro.gunvie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //TODO FrontEnd가 유동 IP라면 어떻게 해야 할까?
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://gunvie.vercel.app","https://gunvie.vercel.app","116.43.252.74","*")
                .allowCredentials(true);
    }
}
