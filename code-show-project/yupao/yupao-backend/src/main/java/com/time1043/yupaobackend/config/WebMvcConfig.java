package com.time1043.yupaobackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 后端跨域配置
 *
 * @author oswin
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 设置允许跨域的路径
                .allowedOrigins("http://localhost:5173")  // 设置允许跨域请求的域名
                .allowCredentials(true)  // 是否允许证书 不再默认开启
                .allowedMethods("*")  // 设置允许的方法
                .maxAge(3600);  // 预检请求的有效期
    }
}
