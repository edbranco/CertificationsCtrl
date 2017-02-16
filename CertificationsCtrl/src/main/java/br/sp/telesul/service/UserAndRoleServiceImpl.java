/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.service;

import br.sp.telesul.exception.LoginFailedException;
import br.sp.telesul.model.UserAndRole;
import br.sp.telesul.repository.UserAndRoleRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ebranco
 */
@Service
public class UserAndRoleServiceImpl implements UserDetailsService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(UserAndRoleServiceImpl.class);

    @Autowired
    UserAndRoleRepository userAndRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws LoginFailedException {
        try {
            UserAndRole userAndRole = userAndRoleRepository.findByUserName(username);
            User user;
            List<GrantedAuthority> authorities = buildUserAuthorities(userAndRole.getAuthorities());
            user = new User(userAndRole.getUserName(), userAndRole.getPassword(), userAndRole.isEnabled(), true, true, true, authorities);

            //return buildUserForAuthentication(userAndRole, authorities);
            return user;
        } catch (Exception e) {
            logger.error("Error in Search User to Login" + e);
            throw new LoginFailedException("User not available");
        }

    }

    private List<GrantedAuthority> buildUserAuthorities(List<String> authorities) {
        List<GrantedAuthority> setAuths = new ArrayList<>();
        for (String auth : authorities) {
            setAuths.add(new SimpleGrantedAuthority(auth));
        }
        return setAuths;
    }

    private User buildUserForAuthentication(UserAndRole userAndRole, List<GrantedAuthority> authorities) {
        return new User(userAndRole.getUserName(), userAndRole.getPassword(), userAndRole.isEnabled(), true, true, true, authorities);
    }
}
