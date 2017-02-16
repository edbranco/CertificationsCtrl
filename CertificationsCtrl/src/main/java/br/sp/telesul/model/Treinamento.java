/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.model;

import br.sp.telesul.jackson.CustomDateDeserializer;
import br.sp.telesul.jackson.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ebranco
 */
@Entity
@Table(name = "TREINAMENTO")
public class Treinamento implements Serializable {
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String codigo;
    
    @Column
    private String Certificadora;
    
    @Column
    private String treinamento;
    
    @Column
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dtInicio;
    
    @Column
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dtTermino;
    
    @Column
    private String versao;

    public Treinamento() {
    }

    public Treinamento(Long id, String codigo, String Certificadora, String treinamento, Date dtInicio, Date dtTermino, String versao) {
        this.id = id;
        this.codigo = codigo;
        this.Certificadora = Certificadora;
        this.treinamento = treinamento;
        this.dtInicio = dtInicio;
        this.dtTermino = dtTermino;
        this.versao = versao;
    }
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getCertificadora() {
        return Certificadora;
    }

    public void setCertificadora(String Certificadora) {
        this.Certificadora = Certificadora;
    }

    public String getTreinamento() {
        return treinamento;
    }

    public void setTreinamento(String treinamento) {
        this.treinamento = treinamento;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtTermino() {
        return dtTermino;
    }

    public void setDtTermino(Date dtTermino) {
        this.dtTermino = dtTermino;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    @Override
    public String toString() {
        return "id=" + id + ", Certificadora=" + Certificadora + ", treinamento=" + treinamento + ", dtInicio=" + dtInicio + ", dtTermino=" + dtTermino + ", versao=" + versao;
    }

}
