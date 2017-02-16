/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ebranco
 */
@Entity
@Table
public class UserAndRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private boolean enabled;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;

    public UserAndRole() {

    }

    public UserAndRole(Long id, String userName, String password, boolean enabled, List<String> authorities) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public boolean isManager() {
        return authorities.contains("ROLE_MANAGER");
    }

    public boolean isAdmin() {
        return authorities.contains("ROLE_ADMIN");
    }

    public boolean isEmployee() {
        return authorities.contains("ROLE_EMPLOYEE");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

}
