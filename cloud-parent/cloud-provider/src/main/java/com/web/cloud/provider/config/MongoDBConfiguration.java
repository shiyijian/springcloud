package com.web.cloud.provider.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.annotation.PreDestroy;
import java.net.UnknownHostException;

/**
 * Created by dragon on 2017/6/27.
 */
@Configuration
public class MongoDBConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.data.mongodb")
    MongoProperties mongoProperties(){
        return new MongoProperties();
    }

    @Autowired
    private MongoProperties properties;
    @Autowired(required = false)
    private MongoClientOptions options;
    private MongoClient mongo;

    @PreDestroy
    public void close() {
        if (this.mongo != null) {
            this.mongo.close();
        }
    }

    @Bean
    public MongoClient mongo() throws UnknownHostException {
        if(this.mongo == null) {
            synchronized (MongoDBConfiguration.class) {
                if(this.mongo == null) {
                    this.mongo = this.properties.createMongoClient(this.options);
                }
            }
        }
        return this.mongo;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        // mongo连接池的参数
        MongoClientOptions.Builder mongoClientOptions =
                MongoClientOptions.builder().socketTimeout(3000).connectTimeout(3000)
                        .connectionsPerHost(50);
        // 设置连接池
//		MongoClientURI mongoClientURI = new MongoClientURI(uri, mongoClientOptions);
        return new SimpleMongoDbFactory(mongo(), properties.getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

}
