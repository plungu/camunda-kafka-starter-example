package com.camunda.poc.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Profile("exec")
@Configuration
public class ExecutorConfig {
    @Bean("fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(1);
    }

    @Bean("singleThreaded")
    public ExecutorService singleThreadedExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean("cachedThreadPool")
    public ExecutorService cachedThreadPool() {
        return Executors.newCachedThreadPool();
    }
}
