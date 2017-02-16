/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Eder Rodrigues
 */
@Controller
public class IndexController {
    @RequestMapping(value="/")
    public ModelAndView index(){
        return new ModelAndView("login");
    }
    @RequestMapping(value="/index")
    public ModelAndView indexMain(){
        return new ModelAndView("index");
    }
    @RequestMapping(value="/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }
    @RequestMapping(value="/cadastrarFuncionario")
    public ModelAndView cadastrarfuncionario(){
        return new ModelAndView("cadastrarFuncionario");
    }
    @RequestMapping(value="/atualizar-funcionario")
    public ModelAndView atualizarfuncionario(){
        return new ModelAndView("atualizar-funcionario");
    }
    @RequestMapping(value="/atualizarFuncionario")
    public ModelAndView atualizarFuncionario(){
        return new ModelAndView("atualizarFuncionario");
    }
    @RequestMapping(value="/searchTemplate")
    public ModelAndView searchTemplate(){
        return new ModelAndView("searchTemplate");
    }
    @RequestMapping(value="/certificationTemplate")
    public ModelAndView certificationTemplate(){
        return new ModelAndView("certificationTemplate");
    }
    @RequestMapping(value="/modalTemplate")
    public ModelAndView modalTemplate(){
        return new ModelAndView("modalTemplate");
    }
    @RequestMapping(value="/modalCertificacaoTemplate")
    public ModelAndView modalCertificacaoTemplate(){
        return new ModelAndView("modalCertificacaoTemplate");
    }
    @RequestMapping(value="/searchCertificacaoTemplate")
    public ModelAndView searchCertificacaoTemplate(){
        return new ModelAndView("searchCertificacaoTemplate");
    }
    @RequestMapping(value="/cadastrarCertificacao")
    public ModelAndView cadastrarCertificacao(){
        return new ModelAndView("cadastrarCertificacao");
    }
    @RequestMapping(value="/atualizarCertificacao")
    public ModelAndView atualizarCertificacao(){
        return new ModelAndView("atualizarCertificacao");
    }
    @RequestMapping(value="/searchAllTemplate")
    public ModelAndView searchAllTemplate(){
        return new ModelAndView("searchAllTemplate");
    }
    @RequestMapping(value="/export")
    public ModelAndView exportTemplate(){
        return new ModelAndView("export");
    }
    @RequestMapping(value="/saveBottomsTemplate")
    public ModelAndView saveBottomsTemplateTemplate(){
        return new ModelAndView("saveBottomsTemplate");
    }
    @RequestMapping(value="/gerenciarUsuarios")
    public ModelAndView gerenciarUsuarios(){
        return new ModelAndView("gerenciarUsuarios");
    }
    @RequestMapping(value="/navBar")
    public ModelAndView navBar(){
        return new ModelAndView("navBar");
    }
    @RequestMapping(value="/j_spring_security_check")
    public ModelAndView j_spring_security_check(){
        return new ModelAndView("index");
    }
    @RequestMapping(value="/loginError")
    public ModelAndView loginError(){
        return new ModelAndView("loginError");
    }
}
