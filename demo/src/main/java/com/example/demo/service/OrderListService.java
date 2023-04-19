package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.enum_model.OrderStatus;
import com.example.demo.model.Order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderListService {

    private Map<Integer, Order> map;

    private final OrderService orderService;


    // private int PREPARING_TIME = 10;
    // private int BOILING_WATER_TIME = 140;
    // private int ADD_INGREDIENTS = 260;
    // private int COOKED_TIME = 261;


    // 테스트를 위해 라면 끓이는 상태 시간 단축
    private int PREPARING_TIME = 10;
    private int BOILING_WATER_TIME = 20;
    private int ADD_INGREDIENTS = 30;
    private int COOKED_TIME = 35;



    @PostConstruct
    public void init() {
        map = new HashMap<>();


        List<Order> orders = orderService.findAll();
        if(orders != null) {
            addAllOrder(orders);
        }

    }

    public void addAllOrder(List<Order> orders) {

        if(orders == null) {
            return;
        }

        for(Order order : orders) {
            map.put(order.getId(), order);
        }
        
    }

    public void addOrder(Order order) {
        if (map.containsKey(order.getId())) {
            log.info("### This order is already exist");
            return;

        }

        map.put(order.getId(), order);

    }


    public Order findOrderById(int id) {

        if(map.isEmpty()) {
            log.info("### OrderList map is empty");
            return null;
        }

        Order order = map.get(id);
        return order;
    }


    public void update() {

        if (map.isEmpty()) {
            //log.info("### orderList is empty");
            return;
        }

        Date current = new Date();

        for (Order order : map.values()) {

            Date orderStartTime = order.getOrderTime();

            long elapsedTime = current.getTime() - orderStartTime.getTime();

            int etSec = (int) (elapsedTime / 1000);


            if(order.getOrderStatus() == OrderStatus.COOKED) {
                continue;
            }

            if(etSec >= COOKED_TIME && order.getOrderStatus() != OrderStatus.COOKED) {
                order.setOrderStatus(OrderStatus.COOKED);
                    orderService.updateOrder(order.getId(), OrderStatus.COOKED);
                    log.info("status update : " + order);
                    continue;
            }

            else if(etSec < ADD_INGREDIENTS && etSec >= BOILING_WATER_TIME && order.getOrderStatus() != OrderStatus.ADD_INGREDIENTS) {
                order.setOrderStatus(OrderStatus.ADD_INGREDIENTS);
                    orderService.updateOrder(order.getId(), OrderStatus.ADD_INGREDIENTS);
                    log.info("status update : " + order);
                    continue;
            }

            else if(etSec < BOILING_WATER_TIME && etSec > PREPARING_TIME && order.getOrderStatus() == OrderStatus.PREPARING) {
                order.setOrderStatus(OrderStatus.BOILING_WATER);
                    orderService.updateOrder(order.getId(), OrderStatus.BOILING_WATER);
                    log.info("status update : " + order);
                    continue;
            }


        }

    }

}
