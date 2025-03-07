/**************************************************************************************** 
 * Z Group | All Rights Reserved                             *
 ****************************************************************************************/
package com.xcmg.i18n.test.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @version 1.0<br>
 * @description: To change this template use File | Settings | File Templates. <br>
 * @author: <a href="bianchangcai@zgroup.com">Bianchangcai</a>
 * @date: 2025-03-05 22:12
 */
@Configuration
public class InternationalizationConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Instantiate the appropriate locale resolution strategy
     *
     * @return locale resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        //for this demo, we'll use a SessionLocaleResolver object
        //as the name implies, it stores locale info in the session
        SessionLocaleResolver resolver = new SessionLocaleResolver();

        //default to US locale
        resolver.setDefaultLocale(Locale.CHINA);

        //get out
        return resolver;
    }


    /**
     * This interceptor allows visitors to change the locale on a per-request basis
     *
     * @return a LocaleChangeInterceptor object
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        //instantiate the object with an empty constructor
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

        //the request param that we'll use to determine the locale
        interceptor.setParamName("lang");

        //get out
        return interceptor;
    }


    /**
     * This is where we'll add the interceptor object
     * that handles internationalization
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

//    @Bean
//    public AbstractConfigurableTemplateResolver templateResolver() {
//        AbstractConfigurableTemplateResolver templateResolver = new ServletContextTemplateResolver(webApplicationContext.getServletContext());
//        //templateResolver.setPrefix("");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML5");
//        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        return templateResolver;
//    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return viewResolver;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

//    @Bean
//    public MessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:*");
//        messageSource.setDefaultEncoding("GBK");
//        return messageSource;
//    }

}
