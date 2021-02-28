package com.vdx.jobService.service;

import com.vdx.jobService.constant.StatisticsType;
import com.vdx.jobService.entity.Statistics;
import com.vdx.jobService.response.GetStatisticsResponse;

import java.util.Date;

public interface StatisticsService {
    GetStatisticsResponse getStatisticsByType(StatisticsType statisticsType);

    Statistics createStatistics(StatisticsType statisticsType, Date timestamp, Long value);
}
