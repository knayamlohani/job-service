package com.vdx.jobService.service;

import com.vdx.jobService.entity.Job;
import com.vdx.jobService.request.CreateJobRequest;

public interface HelperService {
    Job createJobFromCreateJobRequest(CreateJobRequest createJobRequest);
}
