package com.example.blind.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 2:36 PM 2019/7/13
 */
@Data
public class AppVO implements Serializable {

    private long appId;

    private String app;

    private long totalDownloadCount;

    private List<TemplateListItemVo> templates = new LinkedList<>();

}
