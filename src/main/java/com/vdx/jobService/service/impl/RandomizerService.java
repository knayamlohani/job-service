package com.vdx.jobService.service.impl;

import com.vdx.jobService.constant.JobStatus;

import java.util.Random;

public class RandomizerService {

    // generates random no between 0 - 50 and allocates status based on range
    public static JobStatus getStatus () {
        int random = new Random().nextInt(50);

        if(random < 25) {
            return JobStatus.SUCCESS;
        } else  {
            return JobStatus.FAILED;
        }
    }

    // generates a random no between 0 - 100
    public static Integer getJobTime () {
        return new Random().nextInt(100);
    }
}
