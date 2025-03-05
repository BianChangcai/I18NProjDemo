/**************************************************************************************** 
 * Z Group | All Rights Reserved                             *
 ****************************************************************************************/
package com.xcmg.i18n.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description: To change this template use File | Settings | File Templates. <br>
 *
 * @author: <a href="bianchangcai@zgroup.com">Bianchangcai</a>
 * @version 1.0<br>
 * @date: 2025-03-05 22:17
 */
@Controller
public class InternationalController {

    @RequestMapping(value = "international", method = RequestMethod.GET)
    public String international() {
        return "international";
    }

    //redirect to demo if user hits the root
    @RequestMapping("/")
    public String home(Model model) {
        return "redirect:international";
    }

}
