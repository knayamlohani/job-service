package com.vdx.jobService.repository;

import com.vdx.jobService.constant.StatisticsType;
import com.vdx.jobService.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
    List<Statistics> findAllByStatisticsType(StatisticsType statisticsType);

}
