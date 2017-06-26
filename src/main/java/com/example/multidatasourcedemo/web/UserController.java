package com.example.multidatasourcedemo.web;

import com.example.multidatasourcedemo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuguowei on 6/26/17.
 */
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;
    @GetMapping("/")
    public String selectDatabase(String flag){
        return userDao.selectDatabase(flag);
    }
}
