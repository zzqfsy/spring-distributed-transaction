package com.zzqfsy.project.conf;

import com.zzqfsy.project.conf.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 8:22 2018/8/17
 * @Modified By:
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public FilterRegistrationBean encodeFilterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter =new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        registration.setFilter(characterEncodingFilter);
        registration.addUrlPatterns("/*");
        registration.setName("encodeFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean loggingFilterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        LoggingFilter loggingFilter =new LoggingFilter();
        registration.setFilter(loggingFilter);
        registration.addUrlPatterns("/*");
        registration.setName("loggingFilter");
        registration.setOrder(3);
        return registration;
    }
}
