/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.repository;

import br.sp.telesul.model.Funcionario;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ebranco
 */
@Repository
public interface FuncionarioRepository extends BaseRepository<Funcionario, Long> {

    @Query("FROM Funcionario f where f.nome like %?1%")
    @Override
    public Page<Funcionario> findbyattr(String name, Pageable pageable);

    @Query("FROM Funcionario f where f.nome like %?1% and f.habilitado = ?2")
    public Page<Funcionario> findbyAttrFalse(String name, boolean habilitado, Pageable pageable);

    @Query("FROM Funcionario f where f.nome like %?1% and f.habilitado=?2 or f.nome like %?1% and f.habilitado is null")
    public Page<Funcionario> findbyAttrTrue(String name, boolean habilitado, Pageable pageable);

    @Query("SELECT count(f) FROM Funcionario f where f.nome like %?1%")
    @Override
    public Long findbyattrcount(String name);

    @Query("SELECT count(f) FROM Funcionario f where f.nome like %?1% and f.habilitado = ?2 or f.nome like %?1% and f.habilitado is null")
    public Long findbyattrcount(String name, boolean habilitado);

    @Query("SELECT count(f) FROM Funcionario f where f.nome like %?1% and f.habilitado = ?2")
    public Long findbyattrcountFilter(String name, boolean habilitado);
    
    @Query("SELECT DISTINCT f FROM Funcionario f where f.idFuncionario = ?1")
    public Funcionario findFuncionarioById(Long id);
}
