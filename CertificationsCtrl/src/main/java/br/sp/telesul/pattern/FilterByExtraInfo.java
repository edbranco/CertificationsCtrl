/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.pattern;

import br.sp.telesul.model.FilterReturn;
import br.sp.telesul.repository.BaseRepository;
import br.sp.telesul.repository.FuncionarioRepository;
import br.sp.telesul.service.RepositoryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author ebranco
 */
@Service
public class FilterByExtraInfo {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public FilterReturn filter(String filter, Pageable pageRequest, String habilitado) {
        FilterReturn filterReturn = new FilterReturn();
        if (StringUtils.length(filter) > 2) {
            if (checkHabilitado(habilitado)) {
                filterReturn.setTotalEntities(funcionarioRepository.findbyattrcount(filter,checkHabilitado(habilitado)));
                filterReturn.setPage(funcionarioRepository.findbyAttrTrue(filter, checkHabilitado(habilitado), pageRequest));
            } else {
                filterReturn.setTotalEntities(funcionarioRepository.findbyattrcountFilter(filter,checkHabilitado(habilitado)));
                filterReturn.setPage(funcionarioRepository.findbyAttrFalse(filter, checkHabilitado(habilitado), pageRequest));
            }
        } else if (StringUtils.length(filter) <= 2) {
            if (checkHabilitado(habilitado)) {
                filterReturn.setTotalEntities(funcionarioRepository.findbyattrcount(filter,checkHabilitado(habilitado)));
                filterReturn.setPage(funcionarioRepository.findbyAttrTrue(filter, checkHabilitado(habilitado), pageRequest));
            } else {
                filterReturn.setTotalEntities(funcionarioRepository.findbyattrcountFilter(filter,checkHabilitado(habilitado)));
                filterReturn.setPage(funcionarioRepository.findbyAttrFalse(filter, checkHabilitado(habilitado), pageRequest));
            }
        }

        return filterReturn;
    }

    private boolean checkHabilitado(String habilitado) {
        if (habilitado.equalsIgnoreCase("true")) {
            return true;
        }
        return false;

    }
}
