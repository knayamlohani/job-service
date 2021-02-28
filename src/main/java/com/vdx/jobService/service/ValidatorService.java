package com.vdx.jobService.service;

import com.vdx.jobService.exception.BaseException;
import com.vdx.jobService.request.CreateJobRequest;

public interface ValidatorService {
    void validateCreateJobRequest(CreateJobRequest createJobRequest) throws BaseException;
}
