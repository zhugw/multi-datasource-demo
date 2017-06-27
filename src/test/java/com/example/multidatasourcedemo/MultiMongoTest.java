package com.example.multidatasourcedemo;

import lombok.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by zhuguowei on 6/27/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class MultiMongoTest {
    @Autowired
    @Qualifier("fooMongoTemplate")
    private MongoTemplate fooMongoTemplate;
    @Autowired
    @Qualifier("barMongoTemplate")
    private MongoTemplate barMongoTemplate;
    @Autowired
    @Qualifier("testMongoTemplate")
    private MongoTemplate testMongoTemplate;
    @Value("#{${collections}}")
    private Map<String,String> collectionMap;
    @Before
    public void setUp(){
        System.out.println(collectionMap);
        fooMongoTemplate.insert(new User("foo1"),collectionMap.get("foo"));
        fooMongoTemplate.insert(new User("foo2"),collectionMap.get("foo"));
        fooMongoTemplate.insert(new User("3foo"),collectionMap.get("foo"));
        barMongoTemplate.insert(new User("bar1"),collectionMap.get("bar"));
        testMongoTemplate.insert(new User("test1"),collectionMap.get("test"));
    }
    @After
    public void tearDown(){
        fooMongoTemplate.dropCollection(collectionMap.get("foo"));
        barMongoTemplate.dropCollection(collectionMap.get("bar"));
        testMongoTemplate.dropCollection(collectionMap.get("test"));
    }

    @Test
    public void testAggregate(){

        Aggregation aggregation = Aggregation.newAggregation(match(where("name").regex("foo")),
                group().count().as("count"),
                project("count").andExclude("_id"));
        AggregationResults<Count> result = fooMongoTemplate.aggregate(aggregation, collectionMap.get("foo"), Count.class);
        int count = result.getUniqueMappedResult().getCount();
        assertEquals(3, count);
    }
    @Data
    static class Count{
        private int count;
    }
    @Data
    @Document(collection = "testuser")
    static class User{
        private String id;
        private final String name;
        private long createTime = System.currentTimeMillis();
    }
}
