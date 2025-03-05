/**************************************************************************************** 
 * Z Group | All Rights Reserved                             *
 ****************************************************************************************/
package com.xcmg.i18n.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @description:
 * To change this template use File | Settings | File Templates. <br>
 *
 * @author: <a href="bianchangcai@zgroup.com">Bianchangcai</a>
 * @version 1.0<br>
 * @date: 2025-03-05 22:12
 */
public class InternationalizationConfig extends WebMvcConfigurerAdapter {

    /**
     * Instantiate the appropriate locale resolution strategy
     * @return locale resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        //for this demo, we'll use a SessionLocaleResolver object
        //as the name implies, it stores locale info in the session
        SessionLocaleResolver resolver = new SessionLocaleResolver();

        //default to US locale
        resolver.setDefaultLocale(Locale.US);

        //get out
        return resolver;
    }


    /**
     * This interceptor allows visitors to change the locale on a per-request basis
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

}