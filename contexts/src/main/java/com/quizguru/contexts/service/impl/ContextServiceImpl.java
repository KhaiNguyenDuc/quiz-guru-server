package com.quizguru.contexts.service.impl;

import com.quizguru.contexts.amqp.properties.AmqpProducer;
import com.quizguru.contexts.dto.request.GenerateRequest;
import com.quizguru.contexts.dto.response.GenerateContextResponse;
import com.quizguru.contexts.mapper.ContextMapper;
import com.quizguru.contexts.model.Context;
import com.quizguru.contexts.repository.ContextRepository;
import com.quizguru.contexts.service.ContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContextServiceImpl implements ContextService {

    private final AmqpProducer amqpProducer;
    private final ContextRepository contextRepository;

    @Override
    public GenerateContextResponse createContext(GenerateRequest generateRequest) {
        Context context = ContextMapper.generateRequestToContext(generateRequest);
        Context contextSaved = contextRepository.save(context);
        amqpProducer.publish(generateRequest);
        return ContextMapper.contextToGenerateContextResponse(contextSaved);
    }


}
