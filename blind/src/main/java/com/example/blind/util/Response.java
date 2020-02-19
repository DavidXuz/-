package com.example.blind.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 11:51 AM 2019/7/13
 */
@Data
@AllArgsConstructor
public class Response implements Serializable {
    private int code;
    private Object data;

    public Response responseOk(Object obj){
        return new Response(0, obj);
    }
}
