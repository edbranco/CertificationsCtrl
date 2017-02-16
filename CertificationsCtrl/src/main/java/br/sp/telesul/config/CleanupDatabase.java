/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ebranco
 */
public class CleanupDatabase {
    
    private static final Logger logger = LoggerFactory.getLogger(CleanupDatabase.class);

    public static void deregisterDrivers() {
        try {
            logger.info("Destroying Context...");
            try {
                logger.info("Calling MySQL AbandonedConnectionCleanupThread shutdown");
                com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();

            } catch (InterruptedException e) {
                logger.error("Error calling MySQL AbandonedConnectionCleanupThread shutdown ", e);
            }

            ClassLoader cl = Thread.currentThread().getContextClassLoader();

            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();

                if (driver.getClass().getClassLoader() == cl) {

                    try {
                        logger.info("Deregistering JDBC driver " + driver);
                        DriverManager.deregisterDriver(driver);

                    } catch (SQLException ex) {
                        logger.error("Error deregistering JDBC driver" + driver, ex);
                    } catch (Exception ex) {
                        logger.error("Error deregistering JDBC driver" + driver, ex);
                    }

                } else {
                    logger.error("Not deregistering JDBC driver " + driver + " as it does not belong to this webapp's ClassLoader");
                }
            }
        } catch (Exception e) {
            logger.error("Exception in deregistering drivers", e);
        }
    }
}
