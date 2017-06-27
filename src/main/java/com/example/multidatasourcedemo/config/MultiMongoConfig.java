package com.example.multidatasourcedemo.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Created by zhuguowei on 6/26/17.
 */
@Configuration
@EnableConfigurationProperties(MultipleMongoProperties.class)
public class MultiMongoConfig {
    @Autowired
    private MultipleMongoProperties multipleMongoProperties;
    @Bean
    @Primary
    public MongoDbFactory fooFactory() throws Exception {
        MongoProperties mongo = multipleMongoProperties.getFoo();
        return new SimpleMongoDbFactory(new MongoClientURI(mongo.getUri()));
    }
    @Primary
    @Bean(name="fooMongoTemplate")
    public MongoTemplate fooMongoTemplate() throws Exception {
        return new MongoTemplate(fooFactory());
    }
    @Bean
    public MongoDbFactory barFactory() throws Exception {
        MongoProperties mongo = multipleMongoProperties.getBar();
        return new SimpleMongoDbFactory(new MongoClientURI(mongo.getUri()));
    }
    @Bean(name="barMongoTemplate")
    public MongoTemplate barMongoTemplate() throws Exception {
        return new MongoTemplate(barFactory());
    }
    @Bean
    public MongoDbFactory testFactory() throws Exception {
        MongoProperties mongo = multipleMongoProperties.getTest();
        return new SimpleMongoDbFactory(new MongoClientURI(mongo.getUri()));
    }
    @Bean(name="testMongoTemplate")
    public MongoTemplate testMongoTemplate() throws Exception {
        return new MongoTemplate(testFactory());
    }


}
