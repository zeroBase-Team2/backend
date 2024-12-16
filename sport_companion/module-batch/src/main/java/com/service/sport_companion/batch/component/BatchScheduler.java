package com.service.sport_companion.batch.component;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

  private final JobLauncher jobLauncher;
  private final Job mbcCrawlingJob;

  // 매일 11:50, 23:50 mbcCrawlingJob 수행
  @Scheduled(cron = "0 50 11,23 * * *", zone = "Asia/Seoul")
  public void scheduleMbcCrawlingJob() throws Exception {
    JobParameters jobParameters = new JobParametersBuilder()
      .addString("timestamp", String.valueOf(System.currentTimeMillis()))
      .toJobParameters();

    jobLauncher.run(mbcCrawlingJob, jobParameters);
  }
}
