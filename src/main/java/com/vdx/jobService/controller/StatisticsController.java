package com.vdx.jobService.controller;

import com.vdx.jobService.constant.StatisticsType;
import com.vdx.jobService.response.BaseResponse;
import com.vdx.jobService.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    ResponseEntity<BaseResponse> getStatistics(@RequestParam("type") StatisticsType statisticsType) throws Exception {
        return new ResponseEntity<>(
                statisticsService.getStatisticsByType(statisticsType),
                HttpStatus.OK
        );
    }


}
