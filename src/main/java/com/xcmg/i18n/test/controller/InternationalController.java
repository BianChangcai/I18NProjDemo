/**************************************************************************************** 
 * Z Group | All Rights Reserved                             *
 ****************************************************************************************/
package com.xcmg.i18n.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @description: To change this template use File | Settings | File Templates. <br>
 *
 * @author: <a href="bianchangcai@zgroup.com">Bianchangcai</a>
 * @version 1.0<br>
 * @date: 2025-03-05 22:17
 */
@Controller
public class InternationalController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "international", method = RequestMethod.GET)
    public String international(HttpServletRequest request) {
        Object[] args = new Object[1];
        if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.CHINESE.getLanguage())) {
            args[0] = "，徐工!";
        } else if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.US.getLanguage())) {
            args[0] = ", xcmg!";
        }
        request.getSession().setAttribute("hello1", messageSource.getMessage("hello", args, LocaleContextHolder.getLocale()));
        return "international";
    }

    //redirect to demo if user hits the root
    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request) {
        //Locale locale = new Locale(request.getParameterMap().get("lang")[0]);


        //request.getSession().setAttribute(org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
        return "redirect:international";
    }

    @GetMapping("/greeting")
    @ResponseBody
    public String greeting() {
        Object[] args = new Object[1];
        if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.CHINESE.getLanguage())) {
            args[0] = "，徐工!";
        } else if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.US.getLanguage())) {
            args[0] = ", xcmg!";
        }

        return messageSource.getMessage("hello", args, LocaleContextHolder.getLocale());
    }

}
