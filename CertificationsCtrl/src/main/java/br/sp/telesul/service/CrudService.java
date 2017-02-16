/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.service;

import br.sp.telesul.model.FilterReturn;
import br.sp.telesul.model.Funcionario;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author ebranco
 * @param <T>
 */
public interface CrudService<T> {
     public Funcionario save(Funcionario obj);
     public List<Funcionario> findAll();
     public Funcionario findOne(Long id);
     public Funcionario delete(Funcionario obj);
     public Long count();
     public FilterReturn searchPagination(int pageNumber,int pageSize,String filter, String habilitado);
}
