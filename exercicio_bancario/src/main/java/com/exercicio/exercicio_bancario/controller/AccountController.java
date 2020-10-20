package com.exercicio.exercicio_bancario.controller;

import com.exercicio.exercicio_bancario.dto.Account;
import com.exercicio.exercicio_bancario.dto.Client;
import com.exercicio.exercicio_bancario.modelsForForm.FirstAccess;
import com.exercicio.exercicio_bancario.modelsForForm.Password;
import com.exercicio.exercicio_bancario.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @PostMapping("/primeiro/acesso")
    public ModelAndView getFirstAccess(@Valid @ModelAttribute FirstAccess request, BindingResult result){
        final ModelAndView mv;

        Account account = service.search(request.getAgency(),request.getAccount());
        if(result.hasErrors()){
            mv = errorHandling(result, "firstAccess", HttpStatus.BAD_REQUEST);
            mv.addObject("client", service.getByAccount(account));
        }else{

            Client client = service.getByEmailAndCPF(request.getEmail(),request.getCpf());
            if(validateClientAccount(client,account)){
                //Realizar o envio de token para o email*******************************************

                mv = errorHandling(result, "newPassword", HttpStatus.OK);
                mv.addObject("client", service.getByAccount(account));
            }else{
                result.addError(new FieldError("Account", "account", "* Conta não pertence a esse CPF"));
                mv = errorHandling(result, "firstAccess", HttpStatus.BAD_REQUEST);
                mv.addObject("client", service.getByAccount(account));
            }
        }

        return mv;
    }

    private boolean validateClientAccount(Client client, Account account) {
        return client.getAccount() != null &&
                client.getAccount().getAccount().equals(account.getAccount()) &&
                client.getAccount().getAgency().equals(account.getAgency());
    }

    @PostMapping("/gerar/senha")
    public ModelAndView generatePassword(@Valid @ModelAttribute Password request, BindingResult result){
        final ModelAndView mv;

        Client client = service.getByCPF(request.getCpf());
        if(result.hasErrors()){
            mv = errorHandling(result, "newPassword", HttpStatus.BAD_REQUEST);
            mv.addObject("client", client);
        }else{
            if(validatePassword(request.getPassword()) && validateBirthDay(request.getPassword(), client.getBirthday())){
                //Gerar um hash da senha;
               if(service.setPassword(client.getDocument().getCpf(), request.getPassword())){
                   mv = errorHandling(result, "newPassword", HttpStatus.OK);
               }else{
                   result.addError(new FieldError("Password", "password", "* Senhanão foi possivel concluir a acão"));
                   mv = errorHandling(result, "newPassword", HttpStatus.UNPROCESSABLE_ENTITY);
                   mv.addObject("client", client);
               }

                mv.addObject("client", client);
            }else{
                if(validateBirthDay(request.getPassword(), client.getBirthday())){
                    result.addError(new FieldError("Password", "password", "* Senha invalida, é o seu aniversario"));
                }
                if(validatePassword(request.getPassword())){
                    result.addError(new FieldError("Password", "password", "* Senha invalida, 5 valores estao em sequencia"));
                }

                mv = errorHandling(result, "newPassword", HttpStatus.BAD_REQUEST);
                mv.addObject("client", client);
            }

        }

        return mv;
    }

    private Boolean validateBirthDay(String password, String birthday) {
        birthday = birthday.replace("-","");
        if(password.equals(birthday))
            return false;
        return true;
    }


    private boolean validatePassword(String password) {
        int i, j;
        int last, sequence;
        Boolean valid = true;
        char[] characters = password.toCharArray();
        int[] numbers = new int[8];

        int pos = 0;
        for (char item: characters) {
            numbers[pos++] = Character.getNumericValue(item);
        }


        for(i = 0; i < 7; i++){
            last = numbers[i];
            sequence = 0;
            for (j = i+1; j < 8 && sequence > -1; j++){
                if(last+1 == numbers[j]){
                    last++;
                    sequence++;
                }else{
                    sequence = -1;
                }

                if(sequence == 5){
                    valid = false;
                }
            }
        }
        return valid;
    }

}
