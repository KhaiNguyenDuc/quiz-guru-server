package com.quizguru.contexts.service;

import com.quizguru.contexts.dto.ContextDto;
import com.quizguru.contexts.dto.request.GenerateRequest;
import com.quizguru.contexts.dto.response.GenerateContextResponse;

public interface ContextService {
    GenerateContextResponse createContext(GenerateRequest generateRequest);
}
