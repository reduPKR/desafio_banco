package com.exercicio.exercicio_bancario.controller;

import com.exercicio.exercicio_bancario.dto.Client;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cliente")
public class ClientController {
    @GetMapping("/novo")
    public ModelAndView getNewClientPage(){
        final ModelAndView mv = new ModelAndView("newClient");
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView create(@Valid @ModelAttribute Client client, BindingResult result){
        final ModelAndView mv;
        if(result.hasErrors()){
            mv = new ModelAndView("newClient");
            ArrayList<String> errors = getErrors(result);
            mv.addObject("errors", errors);
        }else{
            //Adicionar service
            mv = new ModelAndView("newClient");
        }
        return mv;
    }

    private ArrayList<String> getErrors(BindingResult result){
        ArrayList<String> errors = new ArrayList();

        for(ObjectError error : result.getAllErrors()){
            errors.add(error.getDefaultMessage());
        }
        return errors;
    }
}
