package com.burak.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Controller
public class DefaultController {

    //8@ResponseBody
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if(request.getUserPrincipal().getName().equals("admin@admin.com")) {// gelen requestin içeriği admin@admin.com ise yönlendir
            return "redirect:/admin/home";
        }
        else {
            return "redirect:/user/home";
        }
    }
}
