package com.burak.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class ErrorController {

    //@ResponseBody
    @RequestMapping("/accessdenied")
    public String access(Model model)
    {
        return "access-denied";
    }
}
