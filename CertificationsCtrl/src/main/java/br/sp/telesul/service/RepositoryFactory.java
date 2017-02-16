/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.service;

import br.sp.telesul.repository.BaseRepository;
import br.sp.telesul.repository.FuncionarioRepository;
import br.sp.telesul.repository.UserAndRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ebranco
 */
@Service
public class RepositoryFactory {

    @Autowired
    FuncionarioRepository funcionarioRepository;
    
    UserAndRoleRepository userAndRoleRepository;
    public BaseRepository getRepository(String templateName) {
        switch (templateName) {
            case "Funcionario":
                return funcionarioRepository;
            case "Product":
                return null;

        }
        return null;
    }
}
