package com.quizguru.quizzes.client.record;

import com.quizguru.quizzes.dto.request.RecordRequest;
import com.quizguru.quizzes.dto.request.UpdateRecordRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "RECORDS")
public interface RecordClient {

    @PutMapping("/records/internal/update")
    void updateRecord(UpdateRecordRequest updateRecordRequest);

}
