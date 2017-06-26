package com.example.multidatasourcedemo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * Created by zhuguowei on 6/26/17.
 */
@Configuration
public class MultiDBConfig {

    @Bean(name = "fooDb")
    @ConfigurationProperties(prefix = "foo.datasource")
    @Primary
    public DataSource fooDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "fooJdbcTemplate")
    public JdbcTemplate fooJdbcTemplate(@Qualifier("fooDb") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "barDb")
    @ConfigurationProperties(prefix = "bar.datasource")
    public DataSource barDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "barJdbcTemplate")
    public JdbcTemplate barJdbcTemplate(@Qualifier("barDb") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "testDb")
    @ConfigurationProperties(prefix = "test.datasource")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "testJdbcTemplate")
    public JdbcTemplate testJdbcTemplate(@Qualifier("testDb") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
