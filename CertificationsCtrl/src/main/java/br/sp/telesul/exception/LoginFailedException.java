/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author ebranco
 */
public class LoginFailedException extends UsernameNotFoundException {

    public LoginFailedException(String msg) {
        super("User no available!");

    }
}
