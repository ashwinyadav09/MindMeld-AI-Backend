package com.mywebsite.backend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.mywebsite.backend.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User registerUser(String idToken, String fullName) throws Exception {
        logger.info("Registering user with fullName: {}", fullName);
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            Firestore db = FirestoreClient.getFirestore();
            User user = new User(uid, fullName, email);
            db.collection("users").document(uid).set(user).get(); // Ensure write completes
            logger.info("User data saved to Firestore with UID: {}", uid);
            return user;
        } catch (Exception e) {
            logger.error("Registration failed: {}", e.getMessage(), e);
            throw new Exception("Invalid token", e);
        }
    }

    public User loginUser(String idToken) throws Exception {
        logger.info("Logging in user with ID token");
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            Firestore db = FirestoreClient.getFirestore();
            User user = db.collection("users").document(uid).get().get().toObject(User.class);
            if (user == null) {
                logger.warn("No Firestore data for UID: {}, using token data", uid);
                user = new User(uid, decodedToken.getName(), email);
            }
            logger.info("Login successful for UID: {}", uid);
            return user;
        } catch (Exception e) {
            logger.error("Login failed: {}", e.getMessage(), e);
            throw new Exception("Invalid token", e);
        }
    }
}