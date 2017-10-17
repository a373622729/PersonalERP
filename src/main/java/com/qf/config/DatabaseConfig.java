package com.qf.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ios on 17/10/17.
 */
@Configuration
@MapperScan(basePackages = {"com.qf.mapper"})
public class DatabaseConfig {
    @Bean
    public DruidDataSource druidDataSource(){
        DruidDataSource dataSource =new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("a123456");
        dataSource.setUrl("jdbc:mysql://localhost:3306/PersonalERP?useUnicode=true&characterEncoding=utf8");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
}
