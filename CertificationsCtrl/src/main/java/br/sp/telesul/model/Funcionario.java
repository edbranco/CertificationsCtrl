/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.model;

import br.sp.telesul.jackson.CustomDateDeserializer;
import br.sp.telesul.jackson.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eder Rodrigues
 */
@Entity
@Table(name = "FUNCIONARIO")
public class Funcionario implements Serializable {

    @Id
    @Column(name = "idFuncionario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "dtAdmissao")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dtAdmissao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Formacao> formacoes;

    @Column(name = "area")
    private String area;

    @Column
    private String gestor;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Certificacao> certificacoes;

    // Idioma class idioma e nivel
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Idioma> idiomas;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Treinamento> treinamentos;

    @Column
    private String email;

    @Column
    private String telefone;

    @Column
    private String celular;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserAndRole userandrole;

    @Column(name = "habilitado")
    private Boolean habilitado;

    @Column(name = "dtSaida")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dtSaida;

    public Funcionario() {

    }

    public Funcionario(Long idFuncionario, String nome, String cargo, Date dtAdmissao, List<Formacao> formacoes, String area, String gestor, List<Certificacao> certificacoes, List<Idioma> idiomas, List<Treinamento> treinamentos, String email, String telefone, String celular, UserAndRole userandrole, Boolean habilitado, Date dtSaida) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.cargo = cargo;
        this.dtAdmissao = dtAdmissao;
        this.formacoes = formacoes;
        this.area = area;
        this.gestor = gestor;
        this.certificacoes = certificacoes;
        this.idiomas = idiomas;
        this.treinamentos = treinamentos;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.userandrole = userandrole;
        this.habilitado = habilitado;
        this.dtSaida = dtSaida;
    }

    public Date getDtAdmissao() {
        return dtAdmissao;
    }

    public void setDtAdmissao(Date dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    public List<Formacao> getFormacoes() {
        return formacoes;
    }

    public void setFormacoes(List<Formacao> formacoes) {
        this.formacoes = formacoes;
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public List<Certificacao> getCertificacoes() {
        return certificacoes;
    }

    public void setCertificacoes(List<Certificacao> certificacoes) {
        this.certificacoes = certificacoes;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public List<Treinamento> getTreinamentos() {
        return treinamentos;
    }

    public void setTreinamentos(List<Treinamento> treinamentos) {
        this.treinamentos = treinamentos;
    }

    public UserAndRole getUserandrole() {
        return userandrole;
    }

    public void setUserandrole(UserAndRole userandrole) {
        this.userandrole = userandrole;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Date getDtSaida() {
        return dtSaida;
    }

    public void setDtSaida(Date dtSaida) {
        this.dtSaida = dtSaida;
    }

    @Override
    public String toString() {
        return "Funcionario{" + "idFuncionario=" + idFuncionario + ", nome=" + nome + ", cargo=" + cargo + ", dtAdmissao=" + dtAdmissao + ", formacoes=" + formacoes + ", area=" + area + ", gestor=" + gestor + ", certificacoes=" + certificacoes + ", idiomas=" + idiomas + ", treinamentos=" + treinamentos + ", email=" + email + ", telefone=" + telefone + ", celular=" + celular + ", userandrole=" + userandrole + ", habilitado=" + habilitado + ", dtSaida=" + dtSaida + '}';
    }
}
