package psp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    @Scheduled(cron = "${spring.task.scheduling.corn}")
    public void doCollectAndSendMessage(){
        System.out.println("------------------------");
    }
}
