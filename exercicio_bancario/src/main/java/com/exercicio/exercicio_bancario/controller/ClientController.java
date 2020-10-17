package com.exercicio.exercicio_bancario.controller;

import com.exercicio.exercicio_bancario.dto.Client;
import com.exercicio.exercicio_bancario.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("cliente")
public class ClientController {
    @Autowired ClientService service;

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
            mv.setStatus(HttpStatus.BAD_REQUEST);
            mv.addObject("errors", errors);
        }else{
            service.add(client);
            mv = new ModelAndView("uploadDocument");
            mv.setStatus(HttpStatus.OK);
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
