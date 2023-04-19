package com.example.demo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderScheduleService {


    private final OrderListService orderListService;

    
    @Scheduled(cron = "0/1 * * * * ?")
    public void checkOrderStatus() {
        orderListService.update();
    }

}
