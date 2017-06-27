package com.example.multidatasourcedemo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${spring.datasource.test-while-idle}")
    private boolean  testWhileIdle;
    @Value("${spring.datasource.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.validation-query}")
    private String validationQuery;

    @Bean(name = "fooDb")
    @ConfigurationProperties(prefix = "foo.datasource")
    @Primary
    public DataSource fooDataSource() {
        return commonProcess(DataSourceBuilder.create().build());
    }

    private DataSource commonProcess(DataSource build) {
        org.apache.tomcat.jdbc.pool.DataSource ds = (org.apache.tomcat.jdbc.pool.DataSource) build;
        ds.setTestWhileIdle(testWhileIdle);
        ds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        ds.setValidationQuery(validationQuery);

        return build;
    }

    @Bean(name = "fooJdbcTemplate")
    public JdbcTemplate fooJdbcTemplate(@Qualifier("fooDb") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "barDb")
    @ConfigurationProperties(prefix = "bar.datasource")
    public DataSource barDataSource() {
        return commonProcess(DataSourceBuilder.create().build());
    }

    @Bean(name = "barJdbcTemplate")
    public JdbcTemplate barJdbcTemplate(@Qualifier("barDb") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "testDb")
    @ConfigurationProperties(prefix = "test.datasource")
    public DataSource testDataSource() {
        return commonProcess(DataSourceBuilder.create().build());
    }

    @Bean(name = "testJdbcTemplate")
    public JdbcTemplate testJdbcTemplate(@Qualifier("testDb") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
