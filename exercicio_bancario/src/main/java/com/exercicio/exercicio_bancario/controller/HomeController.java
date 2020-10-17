package com.exercicio.exercicio_bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("")
    public ModelAndView getHomePage(){
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

}
