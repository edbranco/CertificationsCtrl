/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author ebranco
 */
@Configuration

@EnableTransactionManagement
@PropertySource(value = {"classpath:db.properties"})
@EnableJpaRepositories(basePackages = {
    "br.sp.telesul.repository"
})
public class AppConfiguration {
    
    private final Logger logger = LoggerFactory.getLogger(AppConfiguration.class);
    private static final String[] ENTITY_PACKAGES = {
        "br.sp.telesul.model"
    };
    private static final String DB_DRIVER_CLASS = "db.driver";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.username";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String MAX_POOL_SIZE = "c3p0.maxpoolsize";
    private static final String MIN_POOL_SIZE = "c3p0.minpoolsize";
    private static final String ACQUIRE_INCREMENT = "c3p0.acquireIncrement";
    private static final String IDLE_CONN_TEST_PERIOD = "c3p0.idleConnectionTestPeriod";
    private static final String MAX_IDLE_TIME_EXCESS_CONNECTIONS = "c3p0.maxIdleTimeExcessConnections";
    private static final String MAXIDLETIME = "c3p0.maxIdleTime";
    private static final String TEST_CONNECTION_ON_CHECKIN = "c3p0.testConnectionOnCheckin";

    /**
     * Creates and configures the DriverManagerDataSource datasource bean.
     *
     * @param env The runtime environment of our application.
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ComboPooledDataSource dataSource(Environment env) {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(env.getRequiredProperty(DB_DRIVER_CLASS));
            comboPooledDataSource.setJdbcUrl(env.getRequiredProperty(DB_URL));
            comboPooledDataSource.setUser(env.getRequiredProperty(DB_USER));
            comboPooledDataSource.setPassword(env.getRequiredProperty(DB_PASSWORD));
            comboPooledDataSource.setMaxPoolSize(env.getRequiredProperty(MAX_POOL_SIZE, Integer.class));
            comboPooledDataSource.setMinPoolSize(env.getRequiredProperty(MIN_POOL_SIZE, Integer.class));
            comboPooledDataSource.setAcquireIncrement(env.getRequiredProperty(ACQUIRE_INCREMENT, Integer.class));
            comboPooledDataSource.setIdleConnectionTestPeriod(env.getRequiredProperty(IDLE_CONN_TEST_PERIOD, Integer.class));//5 hours
            comboPooledDataSource.setMaxIdleTimeExcessConnections(env.getRequiredProperty(MAX_IDLE_TIME_EXCESS_CONNECTIONS, Integer.class));//6 hours
            comboPooledDataSource.setMaxIdleTime(env.getRequiredProperty(MAXIDLETIME, Integer.class));//7 hours //8 hours for mysql
            comboPooledDataSource.setTestConnectionOnCheckin(env.getRequiredProperty(TEST_CONNECTION_ON_CHECKIN, Boolean.class));
            return comboPooledDataSource;
        } catch (PropertyVetoException ex) {
            logger.error("PropertyVetoException", ex);
        }
        return comboPooledDataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGES);

        Properties jpaProperties = new Properties();

        //Configures the used database dialect. This allows Hibernate to create SQL
        //that is optimized for the used database.
        jpaProperties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));

        //Specifies the action that is invoked to the database when the Hibernate
        //SessionFactory is created or closed.
        jpaProperties.put(HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(HIBERNATE_HBM2DDL_AUTO));

        //Configures the naming strategy that is used when Hibernate creates
        //new database objects and schema elements
        jpaProperties.put(HIBERNATE_NAMING_STRATEGY, env.getRequiredProperty(HIBERNATE_NAMING_STRATEGY));

        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        jpaProperties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));

        //If the value of this property is true, Hibernate will use prettyprint
        //when it writes SQL to the console.
        jpaProperties.put(HIBERNATE_FORMAT_SQL, env.getRequiredProperty(HIBERNATE_FORMAT_SQL));

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    /**
     * Creates the transaction manager bean that integrates the used JPA
     * provider with the Spring transaction mechanism.
     *
     * @param entityManagerFactory The used JPA entity manager factory.
     * @return
     */
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
