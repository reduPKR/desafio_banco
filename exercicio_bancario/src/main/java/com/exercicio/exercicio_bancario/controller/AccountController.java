package com.exercicio.exercicio_bancario.controller;

import com.exercicio.exercicio_bancario.dto.Account;
import com.exercicio.exercicio_bancario.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/acessar")
    public void getAccessAccount(@ModelAttribute Account request){
        System.out.println("chegou");
        System.out.println(request.getAgency());
        System.out.println(request.getAccount());
        if(validateRequest(request)){
            Account account = service.search(request.getAgency(), request.getAccount());

            if(account != null){
                System.out.println("Conta encontrada");
            }else{
                System.out.println("Conta nao encontrada");
            }

        }else{
            System.out.println("Vai ser um requeste invalido");
        }
    }

    private Boolean validateRequest(Account account){
        return account.getAgency() != null && account.getAccount() != null;
    }
}
