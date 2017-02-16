/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.controller;

import br.sp.telesul.model.FilterReturn;
import br.sp.telesul.model.Funcionario;
import br.sp.telesul.model.ObjectReturn;
import br.sp.telesul.repository.UserAndRoleRepository;
import br.sp.telesul.service.CrudService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author Eder Rodrigues
 */
@RequestMapping("/crud")
@Controller
public class CrudController {

    @Autowired
    CrudService<Funcionario> crudService;

    @Autowired
    UserAndRoleRepository userRepository;

//    private PasswordEncoder encoder;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(CrudController.class);

    public void setCrudService(CrudService<Funcionario> crudService) {
        this.crudService = crudService;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Funcionario> save(@RequestBody Funcionario funcionario) throws ParseException {
        try {
            if (funcionario.getIdFuncionario() == null) {
                funcionario.setIdFuncionario(0L);
            }

            if (funcionario.getUserandrole() != null) {
                if (funcionario.getUserandrole().getId() != null) {
                    String senha = userRepository.findPassword(funcionario.getUserandrole().getId());
                    if (!senha.equals(funcionario.getUserandrole().getPassword())) {
                        funcionario.getUserandrole().setPassword(encriptografarSenha(funcionario.getUserandrole().getPassword()));
                    }
                } else {
                    funcionario.getUserandrole().setPassword(encriptografarSenha(funcionario.getUserandrole().getPassword()));
                }
            }
            this.crudService.save(funcionario);
            return new ResponseEntity<>(funcionario, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            logger.error("Error" + " " + e);
            return new ResponseEntity<>(funcionario, HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @RequestMapping(value = "remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Funcionario> remove(@RequestBody Funcionario funcionario) {
        try {
            this.crudService.delete(funcionario);
            return new ResponseEntity<>(funcionario, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error" + e);
            return new ResponseEntity<>(funcionario, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Funcionario> listEmployees() {

        List<Funcionario> employees = this.crudService.findAll();

        return employees;
    }

    @RequestMapping(value = "search/{pageNumber}/{pageSize}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ObjectReturn searchForPagination(@PathVariable int pageNumber, @PathVariable int pageSize, @RequestParam String filter, @RequestParam("habilitado") String habilitado) {

        try {
            FilterReturn fr = crudService.searchPagination(pageNumber, pageSize, filter, habilitado);

            ObjectReturn objr = new ObjectReturn(fr.getTotalEntities(), fr.getPage().getContent());
            return objr;

        } catch (Exception e) {
            logger.error("Error" + e);
            return null;
        }

    }

    @RequestMapping(value = "changePass", method = RequestMethod.POST)
    public void changePass() {
        System.out.println("Test");
        try {
            List<Funcionario> lista = crudService.findAll();
            for (Funcionario f : lista) {
                if (f.getUserandrole() != null) {
                    if (f.getUserandrole().getPassword().length() < 16) {
                        f.getUserandrole().setPassword(encriptografarSenha(f.getUserandrole().getPassword()));
                        crudService.save(f);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error" + " " + e);
        }
    }

    private Pageable createPageRequest(int pageNumber, int pageSize) {
        return new PageRequest(pageNumber, pageSize);
    }

    private String encriptografarSenha(String senha) {
        try {
            byte[] senhaEncriptografada = br.sp.telesul.model.CustomPasswordEncoder.encrypt(senha, "0123456789abcdef");
            String novaSenha = new BASE64Encoder().encode(senhaEncriptografada);
            return novaSenha;
        } catch (Exception e) {
            System.out.println("Error save Password Encripted" + " " + e);
            logger.error("Error save Password Encripted" + " " + e);
            return null;
        }

    }

    private String descriptografarSenha(String senha) {
        try {
            String novaSenha = br.sp.telesul.model.CustomPasswordEncoder.decrypt(senha.getBytes(), "0123456789abcdef");
            return novaSenha;
        } catch (Exception e) {
            System.out.println("Error save Password Encripted" + " " + e);
            logger.error("Error save Password Encripted" + " " + e);
            return null;
        }

    }
}
