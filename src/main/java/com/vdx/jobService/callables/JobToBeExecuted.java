package com.vdx.jobService.callables;

import com.vdx.jobService.constant.JobStatus;
import com.vdx.jobService.model.ProcessJobResponse;
import com.vdx.jobService.repository.JobRepository;
import com.vdx.jobService.service.impl.RandomizerService;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Callable;

@Slf4j
@Data
@ToString
public class JobToBeExecuted implements Callable<ProcessJobResponse> {
    private Integer jobId;
    private JobRepository jobRepository;

    public JobToBeExecuted(Integer jobId, JobRepository jobRepository) {
        this.jobId = jobId;
        this.jobRepository = jobRepository;
    }


    @Override
    public ProcessJobResponse call() {
        Date startTime = new Date();
        log.info("executing job with id: {} at: {}", jobId, startTime);
        try {

            // random sleep for few ms to set job execution time
            Thread.sleep(RandomizerService.getJobTime());
            JobStatus jobStatus = RandomizerService.getStatus();
            Date endTime = new Date();

            log.info("executed job with id: {} at: {}, totalTime: {} ms with status: {}",
                    jobId, endTime, endTime.getTime() - startTime.getTime(), jobStatus
            );
            jobRepository.updateJob(
                    jobId,
                    jobStatus,
                    endTime,
                    startTime,
                    endTime.getTime() - startTime.getTime()
            );
            return new ProcessJobResponse(jobStatus, jobId, new Date());
        } catch (Exception e) {
            log.info("exception while processing job with id: {}, e: {}", jobId, e);
            Date endTime = new Date();
            jobRepository.updateJob(
                    jobId,
                    JobStatus.FAILED,
                    endTime,
                    startTime,
                    endTime.getTime() - startTime.getTime()
            );
            return new ProcessJobResponse(JobStatus.FAILED, jobId, new Date());
        }
    }
}
