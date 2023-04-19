package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Order;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    
    private Order order;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd-HH:mm:ss", timezone="Asia/Seoul")
    private Date versionTime;

    public ResponseDto(Order order, Date versionTime) {
        this.order = order;
        this.versionTime = versionTime;
    }


    
}
