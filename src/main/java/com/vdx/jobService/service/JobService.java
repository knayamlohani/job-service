package com.vdx.jobService.service;

import com.vdx.jobService.constant.JobStatus;
import com.vdx.jobService.exception.BaseException;
import com.vdx.jobService.request.CreateJobRequest;
import com.vdx.jobService.response.CreateJobResponse;
import com.vdx.jobService.response.GetAllJobsResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface JobService {
    CreateJobResponse createJob(CreateJobRequest createJobRequest) throws BaseException;

    GetAllJobsResponse getAllJobs() throws BaseException;

    @Transactional
    List<Integer> fetchJobForProcessing();

    Map<JobStatus, Long> getPercentageSplitUpByStatus();
}
