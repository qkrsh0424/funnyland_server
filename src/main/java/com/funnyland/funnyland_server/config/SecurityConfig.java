package com.funnyland.funnyland_server.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*60)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Value("${spring.redis.host}")
    private String redisAddress;

    @Value("${spring.redis.port}")
    private int redisPort;
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
            .and()
            .cors()
            .and()
            .csrf().csrfTokenRepository(getCookieCsrfTokenRepository());
            // .csrf()
            //     .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisAddress,redisPort);
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        
        return redisTemplate;
    }

    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("STUSEID");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }

    @Bean
    public CrossDomainCookieCsrfTokenRepository getCookieCsrfTokenRepository(){
        CrossDomainCookieCsrfTokenRepository cookieTokenConfig = new CrossDomainCookieCsrfTokenRepository();
        cookieTokenConfig.setCookieName("XSTO");
        cookieTokenConfig.setCookiePath("/");
        cookieTokenConfig.setDomainPattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return cookieTokenConfig;
    }

    // @Bean
    // public RequestCache refererRequestCache() {
    //     return new HttpSessionRequestCache() {
    //         @Override
    //         public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
    //             String referrer = request.getHeader("referer");
    //             if (referrer != null) {
    //                 request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", new SimpleSavedRequest(referrer));
    //             }
    //         }
    //     };
    // }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // source.registerCorsConfiguration("/**", configuration);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
