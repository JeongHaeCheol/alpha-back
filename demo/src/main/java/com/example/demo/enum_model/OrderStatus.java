package com.example.demo.enum_model;



import lombok.AllArgsConstructor;
import lombok.Getter;




@Getter
@AllArgsConstructor
public enum  OrderStatus {
    
    PREPARING(0,"준비중"),
    BOILING_WATER(1,"물 끓이는중"),
    ADD_INGREDIENTS(2,"라면과 스프 넣고 끓이는중"),
    COOKED(3,"라면 완료");


    private Integer Id;

    private String status;

}
