package com.example.blind.vo;

import com.example.blind.model.Template;
import lombok.Data;

import java.io.Serializable;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 2:36 PM 2019/7/13
 */
@Data
public class TemplateListItemVo implements Serializable {

    private String id;

    private String name;

    private String desc;

    private long downloadCount;

    private String template;

    public TemplateListItemVo(Template template){
        this.id = template.get_id();
        this.name = template.getTemplateName();
        this.desc = template.getDesc();
        this.downloadCount = template.getDownloadCount();
    }

    public TemplateListItemVo(String id, String name, String desc, long downloadCount, String template) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.downloadCount = downloadCount;
        this.template = template;
    }
}
