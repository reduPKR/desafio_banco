package com.exercicio.exercicio_bancario.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

public class AbstractController {
    protected ModelAndView errorHandling(BindingResult result, String page, HttpStatus status){
        final ModelAndView mv = new ModelAndView(page);
        ArrayList<String> errors = getErrors(result);
        mv.setStatus(status);
        mv.addObject("errors", errors);
        return  mv;
    }

    private ArrayList<String> getErrors(BindingResult result){
        ArrayList<String> errors = new ArrayList();

        for(ObjectError error : result.getAllErrors()){
            errors.add(error.getDefaultMessage());
        }
        return errors;
    }
}
