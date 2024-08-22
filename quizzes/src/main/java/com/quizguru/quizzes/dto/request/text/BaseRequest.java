package com.quizguru.quizzes.dto.request.text;

public record BaseRequest(
        String userId,
        String quizId,
        String content,
        String htmlContext,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration
) {
    public BaseRequest withId(String userId, String quizId) {
        return new BaseRequest(userId, quizId, content(), htmlContext(), type(), number(), language(), level(), duration());
    }
}
