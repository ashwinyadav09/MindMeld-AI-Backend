package com.mywebsite.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @PostConstruct
    public void initialize() {
        try {
            logger.info("Initializing Firebase...");
            ClassPathResource resource = new ClassPathResource("mywebsite-firebase-adminsdk.json");
            if (!resource.exists()) {
                logger.error("Service account file not found at classpath: mywebsite-firebase-adminsdk.json");
                throw new IllegalStateException("Firebase service account file not found");
            }
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase initialized successfully");
            } else {
                logger.info("Firebase already initialized");
            }
        } catch (Exception e) {
            logger.error("Failed to initialize Firebase: {}", e.getMessage(), e);
            throw new RuntimeException("Firebase initialization failed", e);
        }
    }
}