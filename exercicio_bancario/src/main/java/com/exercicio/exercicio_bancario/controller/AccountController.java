package com.exercicio.exercicio_bancario.controller;

import com.exercicio.exercicio_bancario.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/conta")
public class AccountController {
    @Autowired
    ClientService service;

    @GetMapping("/login")
    public ModelAndView getLoginAccountPage(){
        return new ModelAndView("loginAccount");
    }
}
