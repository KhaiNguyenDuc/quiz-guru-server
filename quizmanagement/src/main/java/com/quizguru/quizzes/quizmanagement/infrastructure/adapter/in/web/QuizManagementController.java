package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web;

import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizCommand;
import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizUseCase;
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

        GenerateQuizResponse responseData =
                TextQuizWebMapper.mapToWebResponse(createTextQuizUseCase.create(command));

        return ApiResponse.created(responseData, "Quiz generated successfully");
    }

}
