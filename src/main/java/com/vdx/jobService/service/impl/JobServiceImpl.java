package com.vdx.jobService.service.impl;

import com.vdx.jobService.constant.JobStatus;
import com.vdx.jobService.entity.Job;
import com.vdx.jobService.exception.BaseException;
import com.vdx.jobService.model.JobStatusPercentage;
import com.vdx.jobService.repository.JobRepository;
import com.vdx.jobService.request.CreateJobRequest;
import com.vdx.jobService.response.CreateJobResponse;
import com.vdx.jobService.response.GetAllJobsResponse;
import com.vdx.jobService.service.HelperService;
import com.vdx.jobService.service.JobService;
import com.vdx.jobService.service.ValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("JobServiceImpl")
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private HelperService helperService;


    @Override
    public CreateJobResponse createJob(CreateJobRequest createJobRequest) throws BaseException {
        log.info("received request to create job: {}", createJobRequest);
        validatorService.validateCreateJobRequest(createJobRequest);
        return new CreateJobResponse(
                jobRepository.save(
                        helperService.createJobFromCreateJobRequest(createJobRequest)
                )
        );
    }


    @Override
    public GetAllJobsResponse getAllJobs() throws BaseException {
        log.info("received request to getAllJobs");
        return new GetAllJobsResponse(jobRepository.findAll());
    }


    @Override
    @Transactional
    public List<Integer> fetchJobForProcessing() {
        List<Integer> jobIds = jobRepository.getAllUnprocessedJobs(JobStatus.QUEUED, new Date()).stream().map(Job::getJobId).collect(Collectors.toList());

        log.info("fetched {} jobs in  {} status", jobIds.size(), JobStatus.QUEUED);

        jobRepository.updateStatusOfJobs(
                JobStatus.IN_PROCESS,
                jobIds
        );

        return jobIds;

    }


    @Override
    public Map<JobStatus, Long> getPercentageSplitUpByStatus() {
        return jobRepository.getPercentageGroupingByStatus()
                .stream()
                .collect(Collectors.toMap(
                        JobStatusPercentage::getJobStatus,
                        JobStatusPercentage::getPercentage
                ));
    }
}
