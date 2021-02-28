package com.vdx.jobService.model;

import com.vdx.jobService.constant.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class JobStatusPercentage {
    private JobStatus jobStatus;
    private Long percentage;
}
