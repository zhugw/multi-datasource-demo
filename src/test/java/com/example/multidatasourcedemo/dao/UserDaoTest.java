package com.example.multidatasourcedemo.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhuguowei on 6/26/17.
 */
@RunWith(SpringRunner.class)
//@JdbcTest(includeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,value=UserDao.class))
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
//@Import(MultiDBConfig.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao dao;
    @Test
    public void selectDatabase() throws Exception {
        String result = dao.selectDatabase("foo");
        assertEquals("foo", result);

        result = dao.selectDatabase("bar");
        assertEquals("bar", result);

        result = dao.selectDatabase("test");
        assertEquals("test", result);
    }

}