package com.quizguru.contexts.mapper;

import com.quizguru.contexts.dto.request.GenerateRequest;
import com.quizguru.contexts.dto.response.GenerateContextResponse;
import com.quizguru.contexts.model.Context;
import com.quizguru.contexts.model.enums.ContextType;
import com.quizguru.contexts.model.enums.Level;

public class ContextMapper {
    public static Context generateRequestToContext(GenerateRequest generateRequest){
        return Context.builder()
                .problemNumber(generateRequest.number())
                .duration(generateRequest.duration())
                .level(Level.valueOf(generateRequest.level()))
                .type(ContextType.valueOf(generateRequest.type()))
                .language(generateRequest.language())
                .build();
    }

    public static GenerateContextResponse contextToGenerateContextResponse(Context context){
        return GenerateContextResponse.builder()
                .problemNumber(context.getProblemNumber())
                .duration(context.getDuration())
                .level(context.getLevel().getValue())
                .type(context.getType().getValue())
                .language(context.getLanguage())
                .build();
    }
}
