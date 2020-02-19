package com.example.blind.model;

import lombok.Data;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 12:00 PM 2019/7/13
 */
@Data
public class Action {

    public static final int FIND_BY_ID = 0;
    public static final int FIND_BY_TEXT = 1;
    public static final int FIND_BY_ID_AND_CLICK = 2;
    public static final int FIND_BY_TEXT_AND_CLICK = 3;
    public static final int FIND_BY_ID_SET_TEXT = 4;

    private int action;
    private String keyword;

}
