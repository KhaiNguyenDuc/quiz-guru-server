package com.quizguru.generates.client.customer;

import com.quizguru.generates.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "CUSTOMERS")
public interface CustomerClient {

    @GetMapping("/customers/internal/users/role")
    ResponseEntity<ApiResponse<List<String>>> findRoleFromUserId(@RequestParam("userId") String userId);
}
