package com.exercicio.exercicio_bancario.controller;

import com.exercicio.exercicio_bancario.dto.Client;
import com.exercicio.exercicio_bancario.dto.DocumentCPF;
import com.exercicio.exercicio_bancario.service.ClientService;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.beans.PropertyEditor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            mv = errorHandling(result, "newClient");
        }else{
            if(service.add(client) != null){
                mv = new ModelAndView("uploadDocument");
                mv.setStatus(HttpStatus.OK);
            }else{
                result.addError(new FieldError("Documento", "CPF", "* CPF já esta sendo utilizado"));
                mv = errorHandling(result, "newClient");
            }
        }
        return mv;
    }

    private ModelAndView errorHandling(BindingResult result, String page){
        final ModelAndView mv = new ModelAndView(page);
        ArrayList<String> errors = getErrors(result);
        mv.setStatus(HttpStatus.BAD_REQUEST);
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

    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("image") MultipartFile image, @RequestParam("cpf") String cpf){
        Client client = service.saveImage(image, cpf);
        final ModelAndView mv;

        if(client != null){
            service.mountImage(client.getDocument().getImage(), client.getDocument().getExtension());

            mv = new ModelAndView("viewDocument");
            mv.addObject("client",client);
            mv.setStatus(HttpStatus.OK);
        }else{
            BindingResult result = new BeanPropertyBindingResult("Documento", "Imagem");
            result.addError(new FieldError("Documento", "CPF", "* CPF erro ao salvar a foto"));
            mv = errorHandling(result, "uploadDocument");
        }

        return mv;
    }
}
