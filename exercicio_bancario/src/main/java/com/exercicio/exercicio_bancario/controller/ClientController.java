package com.exercicio.exercicio_bancario.controller;

import com.exercicio.exercicio_bancario.dto.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("cliente")
public class ClientController {
    @GetMapping("/novo")
    public ModelAndView getNewClientPage(){
        ModelAndView mv = new ModelAndView("newClient");
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView create(@ModelAttribute Client client){
        ModelAndView mv = new ModelAndView("newClient");
        //Adicionar service
        return mv;
    }
}
