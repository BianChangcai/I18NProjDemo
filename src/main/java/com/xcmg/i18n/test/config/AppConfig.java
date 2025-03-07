/**************************************************************************************** 
 * Z Group | All Rights Reserved                             *
 ****************************************************************************************/
package com.xcmg.i18n.test.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @description: <br>
 *
 * @author: <a href="bianchangcai@zgroup.com">Bianchangcai</a>
 * @version 1.0<br>
 * @date: 2025-03-07 22:36
 */
@Configuration
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
