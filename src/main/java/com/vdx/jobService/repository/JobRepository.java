package com.vdx.jobService.repository;

import com.vdx.jobService.constant.JobStatus;
import com.vdx.jobService.entity.Job;
import com.vdx.jobService.model.JobStatusPercentage;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Date;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    @Query("SELECT job FROM Job job WHERE job.jobStatus =:jobStatus AND job.toBeExecutedAt <=:toBeExecutedAt")
    List<Job> getAllUnprocessedJobs(@Param(value = "jobStatus") JobStatus jobStatus,
                                    @Param(value = "toBeExecutedAt") Date toBeExecutedAt);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE Job SET jobStatus=:jobStatus where jobId IN (:jobIds)")
    int updateStatusOfJobs(@Param(value = "jobStatus") JobStatus jobStatus,
                           @Param(value = "jobIds") List<Integer> jobIds);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("UPDATE Job " +
            "SET " +
            "jobStatus=:jobStatus, " +
            "completedAt=:completedAt, " +
            "startedAt=:startedAt, " +
            "executionTime=:executionTime " +
            "WHERE jobId =:jobId")
    int updateJob(@Param(value = "jobId") Integer jobId,
                  @Param(value = "jobStatus") JobStatus jobStatus,
                  @Param(value = "completedAt") Date completedAt,
                  @Param(value = "startedAt") Date startedAt,
                  @Param(value = "executionTime") Long executionTime);


    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("UPDATE Job job " +
            "SET " +
            "job.startedAt=:startedAt " +
            "WHERE " +
            "job.jobId=:jobId")
    Long updateJobStartTime(@Param(value = "jobId") Integer jobId,
                            @Param(value = "startedAt") Date startedAt);

    @Transactional
    @Query("SELECT COUNT(*) FROM Job")
    Long getNoOfJobs();

    @Transactional
    @Query("SELECT AVG(job.executionTime) FROM Job job WHERE job.executionTime <> NULL")
    Long getAverageJobTime();


    @Query(" SELECT " +
            "new com.vdx.jobService.model.JobStatusPercentage(" +
            "jobStatus, " +
            "COUNT(*)*100/ (SELECT COUNT(*) from Job) " +
            ") " +
            "FROM Job " +
            "GROUP BY jobStatus")
    List<JobStatusPercentage> getPercentageGroupingByStatus();

}
