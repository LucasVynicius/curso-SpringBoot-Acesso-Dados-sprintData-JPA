package com.cursoudemyspring.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    /*public DataSource dataSource(){
       DriverManagerDataSource ds = new DriverManagerDataSource();
       ds.setUrl(url);
       ds.setUsername(username);
       ds.setPassword(password);
       ds.setDriverClassName(driver);
       return ds;
    }*/

    @Bean
    public DataSource hikariDataSource(){

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // Máximo de conexões liberadas
        config.setMinimumIdle(1); //Tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); //10 minutos conexão máxima
        config.setConnectionTimeout(60000); // 1 minuto timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // query teste

        return new HikariDataSource(config);
    }
}
