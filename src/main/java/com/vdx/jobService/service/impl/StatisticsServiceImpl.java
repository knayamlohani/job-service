package com.vdx.jobService.service.impl;

import com.vdx.jobService.constant.StatisticsType;
import com.vdx.jobService.entity.Statistics;
import com.vdx.jobService.repository.StatisticsRepository;
import com.vdx.jobService.response.GetStatisticsResponse;
import com.vdx.jobService.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service("StatisticsServiceImpl")
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public GetStatisticsResponse getStatisticsByType(StatisticsType statisticsType) {
        log.info("received request to getStatisticsByType : {}", statisticsType);
        return new GetStatisticsResponse(statisticsRepository.findAllByStatisticsType(statisticsType));
    }

    @Override
    public Statistics createStatistics(StatisticsType statisticsType, Date timestamp, Long value) {
        return statisticsRepository.save(
                new Statistics(
                        statisticsType,
                        timestamp,
                        value
                )
        );
    }


}
