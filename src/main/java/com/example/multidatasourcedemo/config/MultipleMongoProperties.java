package com.example.multidatasourcedemo.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Marcos Barbero
 */
@Data
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MultipleMongoProperties {

	private MongoProperties foo = new MongoProperties();
	private MongoProperties bar = new MongoProperties();
	private MongoProperties test = new MongoProperties();
}
