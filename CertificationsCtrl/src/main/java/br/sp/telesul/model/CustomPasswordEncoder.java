/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.model;

import br.sp.telesul.config.SecurityConfiguration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author ebranco
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomPasswordEncoder.class);
    //Variavéis para encriptografar a senha
    private static String IV = "AAAAAAAAAAAAAAAA";
    private static String chaveEncriptacao = "0123456789abcdef";

    @Override
    public String encode(CharSequence cs) {
        try {
            byte[] textoencriptado = encrypt(cs.toString(), chaveEncriptacao);
            //Base64.encode convert bytes array to String
            return new BASE64Encoder().encode(textoencriptado);
        } catch (Exception ex) {
            System.out.println(ex);
            logger.error("Error in Encode Password" + ex);
            return null;
        }
    }

    @Override
    public boolean matches(CharSequence cs, String encodedPassword) {
        if (encode(cs).equals(encodedPassword)) {
            return true;
        }
        return false;
    }

    public static byte[] encrypt(String senha, String chaveencriptacao) throws Exception {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return encripta.doFinal(senha.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] senha, String chaveencriptacao) throws Exception {
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(decripta.doFinal(senha), "UTF-8");
    }
}
