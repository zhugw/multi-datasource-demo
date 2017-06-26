package com.example.multidatasourcedemo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by zhuguowei on 6/26/17.
 */
@Repository
public class UserDao{
    @Autowired
    @Qualifier("fooJdbcTemplate")
    private JdbcTemplate fooJdbcTemplate;
    @Autowired
    @Qualifier("barJdbcTemplate")
    private JdbcTemplate barJdbcTemplate;
    @Autowired
    @Qualifier("testJdbcTemplate")
    private JdbcTemplate testJdbcTemplate;

    public String selectDatabase(String flag){
        String database = getJdbcTemplate(flag).queryForObject("select database()", String.class);
        return database;
    }

    public JdbcTemplate getJdbcTemplate(String flag){
        switch(flag){
            case "foo" :
                return fooJdbcTemplate;
            case "bar":
                return barJdbcTemplate;
            case "test":
                return testJdbcTemplate;
            default:
                return null;

        }
    }
}
