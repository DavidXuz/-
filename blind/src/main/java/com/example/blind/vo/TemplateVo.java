package com.example.blind.vo;

import com.example.blind.model.Action;
import com.example.blind.model.Template;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 9:36 AM 2019/7/13
 */
@Data
public class TemplateVo implements Serializable {

    private String app;

    private String templateId;

    private String name;

    private String desc;

    private List<Action> actions;

    private long timestamp;

    public TemplateVo(Template template){
        this.app = template.getAppName();
        this.templateId = template.get_id();
        this.name = template.getTemplateName();
        this.desc = template.getDesc();
        this.timestamp = template.getUpdateTime();

        // actions
        this.actions = template.getActions();
    }

}
