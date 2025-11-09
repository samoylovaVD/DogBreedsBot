package services;

import models.UserProfile;
import java.util.HashMap;
import java.util.Map;

public class UserSessionService {
    private Map<Long, UserProfile> userSessions;
    private Map<Long, Integer> currentQuestions;

    public UserSessionService() {
        this.userSessions = new HashMap<>();
        this.currentQuestions = new HashMap<>();
    }

    public UserProfile getOrCreateUserProfile(Long userId) {
        return userSessions.computeIfAbsent(userId, k -> new UserProfile());
    }

    public Integer getCurrentQuestion(Long userId) {
        return currentQuestions.getOrDefault(userId, 0);
    }

    public void setCurrentQuestion(Long userId, Integer questionNumber) {
        currentQuestions.put(userId, questionNumber);
    }

    public void resetUserSession(Long userId) {
        UserProfile profile = userSessions.get(userId);
        if (profile != null) {
            profile.reset();
        }
        currentQuestions.remove(userId);
    }

    public boolean isTestActive(Long userId) {
        return getCurrentQuestion(userId) > 0;
    }
}