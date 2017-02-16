/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.service;

import br.sp.telesul.controller.CrudController;
import br.sp.telesul.model.FilterReturn;
import br.sp.telesul.model.Funcionario;
import br.sp.telesul.pattern.FilterByExtraInfo;
import br.sp.telesul.repository.FuncionarioRepository;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ebranco
 */
@Service
public class CrudServiceImpl implements CrudService<Funcionario> {

    @Autowired
    RepositoryFactory repositoryFactory;
    
    @Autowired
    FilterByExtraInfo filterByExtraInfo;
    
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class);

    @Override
    @Transactional
    public Funcionario save(Funcionario obj) {
        try {
            CrudRepository crudRepository = repositoryFactory.getRepository("Funcionario");
            obj = (Funcionario) crudRepository.save(obj);
            return obj;

        } catch (Exception e) {
            logger.error("Save Error " + e);
            return null;
        }
    }

    @Override
    @Transactional
    public List<Funcionario> findAll() {
        try {
            FuncionarioRepository crudRepository = (FuncionarioRepository) repositoryFactory.getRepository("Funcionario");
            List<Funcionario> funcionarios = crudRepository.findAll();
            return funcionarios;

        } catch (Exception e) {
            logger.error("Find All Error " + e);
            return null;
        }
    }

    @Override
    @Transactional
    public Funcionario findOne(Long id) {
        try {
            FuncionarioRepository crudRepository = (FuncionarioRepository) repositoryFactory.getRepository("Funcionario");
            Funcionario funcionario = crudRepository.findOne(id);
            return funcionario;
        } catch (Exception e) {
            logger.error("Find One Error " + e);
            return null;
        }
    }

    @Override
    @Transactional
    public Funcionario delete(Funcionario obj) {
        try {
            FuncionarioRepository funcionarioRepository = (FuncionarioRepository) repositoryFactory.getRepository("Funcionario");
            funcionarioRepository.delete(obj.getIdFuncionario());
            return obj;
        } catch (Exception e) {
            logger.error("Delete Error " + e);

        }
        return null;
    }

    @Override
    @Transactional
    public Long count() {
        try {
            CrudRepository crudRepository = repositoryFactory.getRepository("Funcionario");
            return crudRepository.count();
        } catch (Exception e) {
            logger.error("Delete Error " + e);
            
            return null;
        }
    }

    @Override
    @Transactional
    public FilterReturn searchPagination(int pageNumber, int pageSize, String filter, String habilitado) {
        Sort sort = sortBy();
        
        Pageable pageRequest = createPage(pageNumber, pageSize, sort);
        FilterReturn filterReturn = filterByExtraInfo.filter(filter,  pageRequest, habilitado);
        return filterReturn;
    }
    
    private Pageable createPage(int pageNumber, int pageSize, Sort sort){
        return new PageRequest(pageNumber-1, pageSize, sort);
    }
    public Sort sortBy() {
        return new Sort(Sort.Direction.ASC, "nome");
    };
}
