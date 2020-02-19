package com.example.blind.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 9:36 AM 2019/7/13
 */
@Data
@Document(collection = "templates")
public class Template implements Serializable {

    public static final String ID = "_id";
    public static final String INDEX = "index";
    public static final String APPID = "appId";
    public static final String APPNAME = "appName";
    public static final String PACKAGENAME = "packageName";
    public static final String TEMPLATENAME = "templateName";
    public static final String DESC = "desc";
    public static final String UPDATETIME = "updateTime";
    public static final String DOWNLOADCOUNT = "downloadCount";
    public static final String ACTIONS = "actions";

    private String _id;

    /**
     * 在app内的内部排序
     */
    private int index;

    private long appId;

    private String appName;

    private String packageName;

    private String templateName;

    private String desc;

    private long updateTime;

    private long downloadCount;

    private List<Action> actions;

}
