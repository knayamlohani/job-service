package com.vdx.jobService.controller;

import com.vdx.jobService.request.CreateJobRequest;
import com.vdx.jobService.response.BaseResponse;
import com.vdx.jobService.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/job")
    ResponseEntity<BaseResponse> createJob(@RequestBody CreateJobRequest createJobRequest) throws Exception {
        return new ResponseEntity<>(
                jobService.createJob(createJobRequest),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/jobs")
    ResponseEntity<BaseResponse> getAllJobs() throws Exception {
        return new ResponseEntity<>(
                jobService.getAllJobs(),
                HttpStatus.OK
        );
    }

}
