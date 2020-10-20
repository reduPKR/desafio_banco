package com.exercicio.exercicio_bancario.controller;

import com.exercicio.exercicio_bancario.dto.Address;
import com.exercicio.exercicio_bancario.dto.Client;
import com.exercicio.exercicio_bancario.dto.DocumentCPF;
import com.exercicio.exercicio_bancario.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("cliente")
public class ClientController extends AbstractController {
    @Autowired
    ClientService service;

    @GetMapping("")
    public ModelAndView getRegisterControl(){
        return new ModelAndView("registrationControl");
    }

    @GetMapping("/novo")
    public ModelAndView getNewClientPage(){
        return new ModelAndView("newClient");
    }

    @PostMapping("/busca")
    public ModelAndView getClientByCpf(@ModelAttribute DocumentCPF document){
        Client client = service.getByCPF(document.getCpf());
        final ModelAndView mv;
        if(client != null){
            mv = redirectRegistrationSteps(client);
        }else{
            BindingResult result = new BeanPropertyBindingResult("Busca", "Cliente");
            result.addError(new FieldError("Client", "CPF", "* Nenhum um cliente encontrado com esse CPF"));
            mv = errorHandling(result, "registrationControl", HttpStatus.BAD_REQUEST);
        }
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView create(@Valid @ModelAttribute Client client, BindingResult result){
        final ModelAndView mv;
        if(result.hasErrors()){
            mv = errorHandling(result, "newClient", HttpStatus.BAD_REQUEST);
        }else{
            if(service.add(client) != null){
                mv = redirectRegistrationSteps(client);
            }else{
                result.addError(new FieldError("Documento", "CPF", "* CPF já esta sendo utilizado"));
                mv = errorHandling(result, "newClient", HttpStatus.BAD_REQUEST);
            }
        }
        return mv;
    }

    @GetMapping("/endereco")
    public ModelAndView getAddressPage(@ModelAttribute DocumentCPF document){
        ModelAndView mv = new ModelAndView("addressRegister");
        mv.addObject("client", service.getByCPF(document.getCpf()));
        return  mv;
    }

    @PostMapping("/endereco")
    public ModelAndView address(@Valid @ModelAttribute("address") Address address, BindingResult result){
        //Gambiarra para evitar de ficar passando parametro na url
        String cpf = address.getId();
        address.setId(null);

        final ModelAndView mv;
        if(result.hasErrors()){
            mv = errorHandling(result, "addressRegister", HttpStatus.BAD_REQUEST);
            mv.addObject("client", service.getByCPF(cpf));
        }else{
            if(service.addAddress(address,cpf)){
                mv = redirectRegistrationSteps(service.getByCPF(cpf));
            }else{
                result.addError(new FieldError("Endereço", "Cliente", "* Cliente não encontrado"));
                mv = errorHandling(result, "addressRegister", HttpStatus.BAD_REQUEST);
                mv.addObject("client", service.getByCPF(cpf));
            }
        }

        return mv;
    }

    @GetMapping("/upload")
    public ModelAndView getUploadDocumentPage(@ModelAttribute DocumentCPF document){
        ModelAndView mv = new ModelAndView("uploadDocument");
        mv.addObject("client", service.getByCPF(document.getCpf()));
        return  mv;
    }

    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("image") MultipartFile image, @RequestParam("cpf") String cpf){
        final ModelAndView mv;
        Client client = service.getByCPF(cpf);
        if(client.getAddress() != null){
            client = service.saveImage(image, cpf);
            if(client != null){
                service.mountImage(client.getDocument().getImage(), client.getDocument().getExtension());
                mv = redirectRegistrationSteps(client);
            }else{
                BindingResult result = new BeanPropertyBindingResult("Documento", "Imagem");
                result.addError(new FieldError("Documento", "CPF", "* CPF erro ao salvar a foto"));
                mv = errorHandling(result, "uploadDocument", HttpStatus.BAD_REQUEST);
                mv.addObject("client", service.getByCPF(cpf));
            }
        }else{
            BindingResult result = new BeanPropertyBindingResult("Documento", "Imagem");
            result.addError(new FieldError("Documento", "Imagem", "* Para carregar um documento é nescessario ter o endereço cadastrado."));
            mv = errorHandling(result, "uploadDocument", HttpStatus.UNPROCESSABLE_ENTITY);
            mv.addObject("client", service.getByCPF(cpf));
        }

        return mv;
    }

    @GetMapping("/informacoes")
    public ModelAndView getViewInformationPage(@ModelAttribute DocumentCPF document){
        final ModelAndView mv;
        Client client = service.getByCPF(document.getCpf());
        if(validateViewInformation(client)){
            mv = new ModelAndView("viewInformation");
            mv.addObject("image",getImagePath(client.getDocument().getExtension()));
        }else{
            BindingResult result = new BeanPropertyBindingResult("Informacoes", "Client");

            if(client.getAddress() == null)
                result.addError(new FieldError("Address", "address", "* Para visualizar as informações é nescessario ter um endereco cadastrado."));
            if(client.getDocument().getImage() == null)
                result.addError(new FieldError("Document", "image", "* Para visualizar as informações é nescessario ter uma foto do documento cadastrado."));
            mv = errorHandling(result, "registrationSteps", HttpStatus.BAD_REQUEST);
        }

        mv.addObject("client", client);
        return mv;
    }

    private Boolean validateViewInformation(Client client){
        return client.getAddress() != null && client.getDocument().getImage() != null;
    }

    private String getImagePath(String extension){
        return "/image/cpf."+extension;
    }

    private ModelAndView redirectRegistrationSteps(Client client){
        final ModelAndView mv = new ModelAndView("registrationSteps");
        mv.addObject("client", client);
        mv.setStatus(HttpStatus.OK);
        return  mv;
    }

    @GetMapping("/finalizar")
    private ModelAndView getCreateAccount(@ModelAttribute DocumentCPF document){
        Client client = service.getByCPF(document.getCpf());
        client.setAccount(service.createAccount());

        final ModelAndView mv = new ModelAndView("accountInformation");
        mv.addObject("client", client);
        mv.setStatus(HttpStatus.OK);
        return  mv;
    }
}
