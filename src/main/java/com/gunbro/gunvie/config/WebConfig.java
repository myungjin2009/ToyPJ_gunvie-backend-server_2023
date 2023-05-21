package com.gunbro.gunvie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://gunvie.vercel.app","https://gunvie.vercel.app","116.43.252.74","*")
                .allowCredentials(true);
        //.allowedOrigins("http://gunvie.vercel.app","https://gunvie.vercel.app","116.43.252.74","*")
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
