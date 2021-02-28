package com.vdx.jobService.service.impl;

import com.vdx.jobService.callables.JobToBeExecuted;
import com.vdx.jobService.constant.JobStatus;
import com.vdx.jobService.constant.StatisticsType;
import com.vdx.jobService.repository.JobRepository;
import com.vdx.jobService.service.JobService;
import com.vdx.jobService.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service("JobSchedulerService")
public class JobSchedulerService {


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private JobService jobService;

    @Value("${job.process-pending-tasks.pool.size}")
    private Integer processPendingJobsPoolSize;

    private ExecutorService processPEndingJobsExecutorService;


    @PostConstruct
    void init() {
        processPEndingJobsExecutorService = Executors.newFixedThreadPool(processPendingJobsPoolSize);
    }

    @Scheduled(fixedRateString = "${job.process-pending-tasks.poll-time}")
    public void processPendingJobs() {
        log.info("processing jobs");


        try {
            List<JobToBeExecuted> jobsToBeExecuted = jobService.fetchJobForProcessing()
                    .stream()
                    .map((jobId) -> new JobToBeExecuted(jobId, jobRepository))
                    .collect(Collectors.toList());

            processPEndingJobsExecutorService.invokeAll(jobsToBeExecuted);
        } catch (Exception e) {
            log.error("failed to fetch and process jobs message: {}, e: {}", e.getMessage(), e);
        }
    }


    @Scheduled(fixedRateString = "${job.count-stats.poll-time}")
    public void generateCountStats() {
        try {
            log.info("generateCountStats");
            statisticsService.createStatistics(StatisticsType.JOB_COUNT, new Date(), jobRepository.getNoOfJobs());
        } catch (Exception e) {
            log.error("failed to generateCountStats message: {}, e: {}", e.getMessage(), e);
        }
    }

    @Scheduled(fixedRateString = "${job.avg-time-stats.poll-time}")
    public void generateAvgTimeStats() {
        try {
            log.info("generateAvgTimeStats");
            statisticsService.createStatistics(StatisticsType.AVG_JOB_TIME, new Date(), jobRepository.getAverageJobTime());
        } catch (Exception e) {
            log.error("failed to generateAvgTimeStats message: {}, e: {}", e.getMessage(), e);
        }
    }

    @Scheduled(fixedRateString = "${job.percentage-splitup-by-status-stats.poll-time}")
    public void generateStatusRateStats() {
        try {
            log.info("generateFailureStats");

            Map<JobStatus, Long> map = jobService.getPercentageSplitUpByStatus();

            log.info("generateFailureStats map: {}", map);


            statisticsService.createStatistics(StatisticsType.SUCCESS_RATE, new Date(), map.get(JobStatus.SUCCESS));
            statisticsService.createStatistics(StatisticsType.FAILURE_RATE, new Date(), map.get(JobStatus.FAILED));
        } catch (Exception e) {
            log.error("failed to generateStatusRateStats message: {}, e: {}", e.getMessage(), e);
        }
    }
}
