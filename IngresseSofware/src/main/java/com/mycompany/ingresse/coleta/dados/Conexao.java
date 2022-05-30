/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ingresse.coleta.dados;

import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author isaque.santos@VALEMOBI.CORP
 */
public class Conexao {
    private BasicDataSource dataSource;
    private JdbcTemplate jdbc;
   
    private BasicDataSource dataSourceSql;
    private JdbcTemplate jdbcSql;
    
    public Conexao(){
    this.dataSource = new BasicDataSource();
    dataSource​.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    dataSource​.setUrl("jdbc:sqlserver://ingresse-srv.database.windows.net:1433;database=ingresse-bd;user=ingresseAdmin@ingresse-srv;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
    dataSource​.setUsername("ingresseAdmin");
    dataSource​.setPassword("2adsb#grupo5");
    this.jdbc = new JdbcTemplate(dataSource);
    
    
    this.dataSourceSql = new BasicDataSource();
    dataSourceSql​.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSourceSql​.setUrl("jdbc:mysql://localhost:3306/logs-ingresse?autoReconnect=true&useSSL=false");
    dataSource​.setUsername("root");
    dataSource​.setPassword("urubu100");
    this.jdbcSql = new JdbcTemplate(dataSourceSql);
    
    }
    

    public JdbcTemplate getJdbc() {
        return jdbc;
    }
    
     public JdbcTemplate getJdbcSql() {
        return jdbcSql;
    }


    
}
