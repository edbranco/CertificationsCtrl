/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

/**
 *
 * @author ebranco
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        final StringBuffer url = request.getRequestURL();
        HttpSession session = request.getSession();
        if (url.indexOf("j_spring_security_check") > -1) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        if (accessDeniedException.getClass().isAssignableFrom(InvalidCsrfTokenException.class)
                || accessDeniedException.getClass().isAssignableFrom(MissingCsrfTokenException.class)) {
            if (url.indexOf("index") > -1) {
                return;
            }
            logger.info("CustomAccessDeniedHandler:: Exception class:{}", accessDeniedException.getClass());
            logger.info("CustomAccessDeniedHandler:: Access is denied:{}", accessDeniedException.getMessage());
            logger.info("CustomAccessDeniedHandler:: Access is denied for page :{}", request.getRequestURL());

            if (url.indexOf("j_spring_security_logout") > -1 || url.indexOf("views") > -1) {
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect(request.getContextPath() + "/login?expired");
            } else if (url.indexOf("views") > -1) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("ACCESS_DENIED");
                response.flushBuffer();
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("ACCESS_DENIED");
                response.flushBuffer();
            }
        }
    }
}
