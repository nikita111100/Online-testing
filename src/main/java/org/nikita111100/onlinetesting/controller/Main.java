package org.nikita111100.onlinetesting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {

    @GetMapping("/")
    public String main(){
        return "main";
    }
}
