package com.example.kunuz.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityFilterConfig {
    private final TokenFilter tokenFilter;

    public SecurityFilterConfig(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {

        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(tokenFilter);
        bean.addUrlPatterns("/profile/*");
        bean.addUrlPatterns("/article_type/*");
        bean.addUrlPatterns("/region/*");
        bean.addUrlPatterns("/email_history/*");
        bean.addUrlPatterns("/article/admin/*");
        bean.addUrlPatterns("/article_like/*");
        bean.addUrlPatterns("/saved_article/*");
        return bean;


    }
}
