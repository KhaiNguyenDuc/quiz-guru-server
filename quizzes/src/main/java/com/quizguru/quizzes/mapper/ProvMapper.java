package com.quizguru.quizzes.mapper;

import com.quizguru.quizzes.dto.request.RecordItemRequest;
import com.quizguru.quizzes.dto.request.text.BaseRequest;
import com.quizguru.quizzes.dto.response.RecordItemResponse;
import com.quizguru.quizzes.model.Quiz;
import com.quizguru.quizzes.model.enums.Level;
import com.quizguru.quizzes.model.enums.QuizType;

import java.util.List;

public class ProvMapper {
        public static Quiz toQuiz(BaseRequest baseRequest){
            return Quiz.builder()
                    .number(baseRequest.number())
                    .duration(baseRequest.duration())
                    .level(Level.valueOf(baseRequest.level()))
                    .type(QuizType.valueOf(baseRequest.type()))
                    .language(baseRequest.language())
                    .givenText(baseRequest.content())
                    .isDeleted(false)
                    .build();
        }

    public static List<RecordItemResponse> toRecordItemResponses(List<RecordItemRequest> recordItemRequests) {
        return null;
    }
}
