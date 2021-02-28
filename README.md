# Job Service

### SET UP
```
1 git clone https://github.com/knayamlohani/job-service.git
2 cd job-service
3 git checkout dev_v1
4 mvn clean install
5 java -jar target/job-service-1.0.0.jar
```



### APIs

1 -  create job execution
```

curl --location --request POST 'http://127.0.0.1:9191/job-service/api/v1/job' \
--header 'Content-Type: application/json' \
--data-raw '{
    "executionTimestamp": 1614450999932
}'

executionTimestamp - time in ms at which job should be executed
```


2 - get all jobs
```
curl --location --request GET 'http://127.0.0.1:9191/job-service/api/v1/jobs'
```

3 - get statistics
```

curl --location --request GET 'http://127.0.0.1:9191/job-service/api/v1/statistics?type=JOB_COUNT'
curl --location --request GET 'http://127.0.0.1:9191/job-service/api/v1/statistics?type=AVG_JOB_TIME'
curl --location --request GET 'http://127.0.0.1:9191/job-service/api/v1/statistics?type=SUCCESS_RATE'
curl --location --request GET 'http://127.0.0.1:9191/job-service/api/v1/statistics?type=FAILURE_RATE'

```

### JOBS
```
* process-pending-tasks
* count-stats
* avg-time-stats
* percentage-splitup-by-status-stats
```


### Controllers

* JobController
* StatisticsController


### Services

* JobService
* RandomizerService
* StatisticsService
* JobSchedulerService
* ExceptionHelperService
* HelperService
* ValidatorService



### Aspects

* ExceptionHandler


### all the data is kept in h2 database which is persisted to file in database folder

```

all configs are in application.yml

pool size for parallel job processing
frequency at which jobs run
frequency at which statistics generation jobs run


process pending job - runs every 1 sec(1000ms), pool size is kept 3 (so can process max 3 jobs at a time, can be updated to handle more for better processing)
stats generation jobs - run every 10 sec(10000ms)

```


### Assumptions and points
```
1 - status of a job (success/fail) is randomly assigned (RandomizerService)
2 - execution time of job is set by sleep of of n ms (randomly generated) (RandomizerService)
3 - THREAD SAFETY is maintained by fetching pending jobs in a transaction (by acquiring a lock) 
and as soon they are fetched their status is updated to in_process within the transaction 
to prevent reallocation to another job (JobService.fetchJobForProcessing)

```