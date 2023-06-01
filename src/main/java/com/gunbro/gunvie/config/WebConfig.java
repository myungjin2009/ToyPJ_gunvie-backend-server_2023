package com.gunbro.gunvie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //프론트엔드 측 로컬 개발 시 CORS 문제가 발생하여 , allowedOrigins에 "*"가 들어가있음. 보안취약
    //TODO allowedOrigins "*"를 제외할 방법
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://gunvie.vercel.app","https://gunvie.vercel.app","116.43.252.74","*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/post_images/**")
                .addResourceLocations("classpath:/post_images/")
                .setCachePeriod(30);

        registry.addResourceHandler("/profile_images/**")
                .addResourceLocations("classpath:/profile_images/")
                .setCachePeriod(30);
    }
}
