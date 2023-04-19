package com.example.demo.controller;



import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.ResponseDto;
import com.example.demo.model.Order;
import com.example.demo.service.OrderListService;
import com.example.demo.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:8089",allowedHeaders = "*")
public class OrderController {

    private final OrderService orderService;
    private final OrderListService orderListService;



    @GetMapping("/")
    public ResponseEntity home() {
        return ResponseEntity.ok("Home");
    }



    // 주문하기
    @PostMapping("/order")
    public ResponseEntity postOrder() {

        Order order = orderService.createOrder();
        orderListService.addOrder(order);


        Date now = new Date();

        ResponseDto responseDto = new ResponseDto(order, now);

        if (order == null) {
            return ResponseEntity.ok("createOrder failed");
        }




        return ResponseEntity.ok(responseDto);

    }

    // 상태문의
    @GetMapping("/order")
    public ResponseEntity getOrder(@RequestParam int orderNum) {

        Order orderInMemory = orderListService.findOrderById(orderNum);
        

        Date now = new Date();

        

        // 메모리상에 데이터가 없을 경우 DB에서 가져온다.
        // 시스템이 재시작되거나 할 경우 메모리가 초기화 될 수 있음.
        if(orderInMemory == null) {

            Order orderInDB = orderService.findOrderById(orderNum);
           
            if(orderInDB == null) {
                return ResponseEntity.ok("getOrder failed");
            }

            // DB의 order를 메모리상에 보관
            orderListService.addOrder(orderInDB);

            ResponseDto responseDto = new ResponseDto(orderInDB, now);

            return ResponseEntity.ok(responseDto);
        }

        ResponseDto responseDto = new ResponseDto(orderInMemory, now);
        
        return ResponseEntity.ok(responseDto);

    }

}
