package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.enum_model.OrderStatus;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;




    public List<Order> findAll(){
        return orderRepository.findAll();
    }


    public Order updateOrder(int id, OrderStatus orderStatus) {

        Order order = orderRepository.findOrderById(id);
        
        if(order != null) {
            order.setOrderStatus(orderStatus);
            Order result = orderRepository.saveAndFlush(order);
            return result;
        }

        else {
            log.info("### updateOrder failed : " + id + " order not founded");
            return null;
        }
    
    }


    public Order createOrder() {
        Order order = new Order();
        order.setOrderTime(new Date());
        order.setOrderStatus(OrderStatus.PREPARING);

        Order result = orderRepository.saveAndFlush(order);

        log.info("### result  = " + result);
        return result;

    }

    public Order findOrderById(int id) {
        Order order = orderRepository.findOrderById(id);

        if(order == null) {
            log.info("### findOrderById failed : " + id + " order not founded");
        }
        
        return order;
    }

    
}
