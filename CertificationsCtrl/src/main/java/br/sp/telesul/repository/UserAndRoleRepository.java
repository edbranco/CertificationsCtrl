/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.repository;

import br.sp.telesul.model.UserAndRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ebranco
 */
@Repository
public interface UserAndRoleRepository extends BaseRepository<UserAndRole, Long> {
    @Query("FROM UserAndRole e where e.userName =?1")
    public UserAndRole findByUserName(String userName);

    @Query("FROM UserAndRole e where e.userName like %?1%")
    @Override
    public Page<UserAndRole> findbyattr(String name, Pageable pageable);

    @Query("SELECT count(e) FROM UserAndRole e where e.userName like %?1%")
    @Override
    public Long findbyattrcount(String name);
    
    @Query("SELECT u.password from UserAndRole u where u.id=?1")
    public String findPassword(Long id);
}
