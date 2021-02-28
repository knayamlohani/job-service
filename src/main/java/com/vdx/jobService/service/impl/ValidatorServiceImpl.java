package com.vdx.jobService.service.impl;

import com.vdx.jobService.exception.BadRequestException;
import com.vdx.jobService.exception.BaseException;
import com.vdx.jobService.request.CreateJobRequest;
import com.vdx.jobService.service.ValidatorService;
import org.springframework.stereotype.Service;

@Service("ValidatorServiceImpl")
public class ValidatorServiceImpl implements ValidatorService {
    @Override
    public void validateCreateJobRequest(CreateJobRequest createJobRequest) throws BaseException {
        if (createJobRequest == null) {
            throw new BadRequestException("request cannot be null");
        }

        if (createJobRequest.getExecutionTimestamp() == null || createJobRequest.getExecutionTimestamp() <= 0) {
            throw new BadRequestException("ExecutionTimestamp cannot be null or < 0");
        }
    }
}
