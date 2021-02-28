package com.vdx.jobService.model;

import com.vdx.jobService.constant.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProcessJobResponse {
    private JobStatus jobStatus;
    private Integer jobId;
    private Date executedAt;
}
