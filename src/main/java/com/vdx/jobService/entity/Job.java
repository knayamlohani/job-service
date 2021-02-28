package com.vdx.jobService.entity;


import com.vdx.jobService.constant.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "JOB")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "job_id_generator")
    @SequenceGenerator(name="job_id_generator", sequenceName = "JOB_ID_SEQUENCE")
    @Column(name="JOB_ID", length=30, unique=true, nullable = false)
    private Integer jobId;

    @Enumerated(EnumType.STRING)
    @Column(name = "JOB_STATUS")
    private JobStatus jobStatus;

    @Column(name = "TO_BE_EXECUTED_AT")
    private Date toBeExecutedAt;

    @Column(name = "STARTED_AT")
    private Date startedAt;

    @Column(name = "COMPLETED_AT")
    private Date completedAt;

    @Column(name = "EXECUTION_TIME")
    private Long executionTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED")
    private Date updated;



}
