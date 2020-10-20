package com.exercicio.exercicio_bancario.controller;

import com.exercicio.exercicio_bancario.dto.Account;
import com.exercicio.exercicio_bancario.dto.Client;
import com.exercicio.exercicio_bancario.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/conta")
public class AccountController extends AbstractController{
    @Autowired
    ClientService service;

    @GetMapping("/login")
    public ModelAndView getLoginAccountPage(){
        return new ModelAndView("loginAccount");
    }

    @PostMapping("/acessar")
    public ModelAndView getAccessAccount(@ModelAttribute Account request){
        final ModelAndView mv;
        if(validateRequest(request)){
            Account account = service.search(request.getAgency(), request.getAccount());
            if(account != null){
                Client client = service.getByAccount(account);

                if(account.getPassword() == null){
                    mv = new ModelAndView("firstAccessPage");
                    mv.addObject("client",client);
                    mv.setStatus(HttpStatus.OK);
                }else{
                    return new ModelAndView("loginAccount");
                }

            }else{
                BindingResult result = new BeanPropertyBindingResult("Login", "Account");
                result.addError(new FieldError("Account", "account", "* Conta não encontrada"));
                mv = errorHandling(result, "loginAccount", HttpStatus.BAD_REQUEST);
            }
        }else{
            BindingResult result = new BeanPropertyBindingResult("Login", "Account");

            if(request.getAgency().equals(""))
                result.addError(new FieldError("Account", "agencia", "* Agencia é nescessaria para realizar o login"));
            if(request.getAccount().equals(""))
                result.addError(new FieldError("Account", "account", "* Conta é nescessaria para realizar o login"));
            mv = errorHandling(result, "loginAccount", HttpStatus.BAD_REQUEST);
        }
        return mv;
    }

    private Boolean validateRequest(Account account){
        return !account.getAgency().equals("") && !account.getAccount().equals("");
    }

}
