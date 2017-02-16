/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 *
 * @author ebranco
 */
public class Check403 implements AuthenticationEntryPoint{

    private final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Override
    public final void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        HttpSession session = request.getSession();
        final StringBuffer url = request.getRequestURL();
        logger.info("Check403::Exception class:{}", authException.getClass());
        logger.info("Check403::Message of exception:{}", authException.getMessage());
        logger.info("Check403::Access is denied for page :{}", request.getRequestURL());
        if (url.indexOf("j_spring_security_logout") > -1 || url.indexOf("pages") > -1) {
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login?expired");
        } else if (url.indexOf("/crud") > -1 || url.indexOf("/assignment") > -1 || url.indexOf("/status") > -1) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("ACCESS_DENIED");
            response.flushBuffer();
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }

    }
}
