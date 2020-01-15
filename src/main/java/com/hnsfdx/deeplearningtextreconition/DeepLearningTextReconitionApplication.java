package com.hnsfdx.deeplearningtextreconition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DeepLearningTextReconitionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeepLearningTextReconitionApplication.class, args);
    }

}
