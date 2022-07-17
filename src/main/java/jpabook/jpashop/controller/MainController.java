package jpabook.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/login")
    public String getLoginForm() {
        return "loginPage";
    }

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/main")
    public String getMain() {
        return "main";
    }
}