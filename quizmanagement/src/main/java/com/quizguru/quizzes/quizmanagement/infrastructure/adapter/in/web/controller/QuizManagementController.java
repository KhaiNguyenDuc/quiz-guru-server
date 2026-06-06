package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.controller;

import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizCommand;
import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizResult;
import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizUseCase;
import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.response.ApiResponse;
import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.response.GenerateQuizResponse;
import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.request.TextQuizRawWebRequest;
import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.mapping.TextQuizWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizManagementController {

    private final CreateTextQuizUseCase createTextQuizUseCase;

    @PostMapping("/text")
    public ResponseEntity<ApiResponse<GenerateQuizResponse>> createQuizByText(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody TextQuizRawWebRequest textQuizRawWebRequest
    ){
        CreateTextQuizCommand command = new CreateTextQuizCommand(
                userId,
                textQuizRawWebRequest.type(),
                textQuizRawWebRequest.number(),
                textQuizRawWebRequest.language(),
                textQuizRawWebRequest.level(),
                textQuizRawWebRequest.duration(),
                textQuizRawWebRequest.content(),
                textQuizRawWebRequest.htmlContext()
        );

        CreateTextQuizResult createTextQuizResult = createTextQuizUseCase.create(command);

        GenerateQuizResponse responseData =
                TextQuizWebMapper.mapToWebResponse(createTextQuizResult);

        return ApiResponse.created(responseData, "Quiz generated successfully");
    }

}
