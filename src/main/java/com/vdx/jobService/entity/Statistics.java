package com.vdx.jobService.entity;

import com.vdx.jobService.constant.StatisticsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "STATISTICS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "stat_id_generator")
    @SequenceGenerator(name="stat_id_generator", sequenceName = "STAT_ID_SEQUENCE")
    @Column(name="ID", length=30, unique=true, nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATISTIC_TYPE")
    private StatisticsType statisticsType;

    @Column(name = "TIMESTAMP")
    private Date timeStamp;

    @Column(name = "STATISTIC_VALUE")
    private Long value;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED")
    private Date updated;


    public Statistics(StatisticsType statisticsType, Date timeStamp, Long value) {
        this.statisticsType = statisticsType;
        this.timeStamp = timeStamp;
        this.value = value;
    }
}