package com.funnyland.funnyland_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    @Value("${app.external.assets.path}")
    String assetsPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        registry
            .addResourceHandler("/uploads/**")
            // .addResourceLocations("file:///Users/sehoon/work/PCY/funnyland/funnyland_server/src/main/webapp/uploads/")
            .addResourceLocations("file:///"+assetsPath)
            .setCachePeriod(0);
    }
}
