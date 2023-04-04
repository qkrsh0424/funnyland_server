package com.funnyland.funnyland_server.config;

import com.funnyland.funnyland_server.interceptor.RequiredLoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer{
    private final RequiredLoginInterceptor requiredLoginInterceptor;

//    @Value("${app.external.assets.path}")
//    String assetsPath;
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // TODO Auto-generated method stub
//        registry
//            .addResourceHandler("/uploads/**")
//            // .addResourceLocations("file:///Users/sehoon/work/PCY/funnyland/funnyland_server/src/main/webapp/uploads/")
//            .addResourceLocations("file:///"+assetsPath)
//            .setCachePeriod(0);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requiredLoginInterceptor);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Spring boot 2.2.x 이후부터 default charset이 ISO 8859-1 로 변경됨으로써, 직접 default charset을 utf-8로 변경해줘야 됨.
        converters.stream().filter(converter -> converter instanceof MappingJackson2HttpMessageConverter).findFirst()
                .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8));
    }
}
