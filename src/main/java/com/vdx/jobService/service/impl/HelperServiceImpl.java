package com.vdx.jobService.service.impl;

import com.vdx.jobService.constant.JobStatus;
import com.vdx.jobService.entity.Job;
import com.vdx.jobService.request.CreateJobRequest;
import com.vdx.jobService.service.HelperService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("HelperServiceImpl")
public class HelperServiceImpl implements HelperService {
    @Override
    public Job createJobFromCreateJobRequest(CreateJobRequest createJobRequest) {
        Job job = new Job();

        job.setJobStatus(JobStatus.QUEUED);
        job.setToBeExecutedAt(new Date(createJobRequest.getExecutionTimestamp()));

        return job;
    }
}
