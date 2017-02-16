/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author ebranco
 */
@Component
@WebListener
public class AppServletContextListener implements ServletContextListener{

    private static final Logger logger = LoggerFactory.getLogger(AppServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initialising Context...");
    }

    @Override
    public final void contextDestroyed(ServletContextEvent sce) {
        CleanupDatabase.deregisterDrivers();
    }
}
