package com.talkover.model;

import java.util.List;

/**
 * Created by heleninsa on 2019-07-13.
 *
 * @author heleninsa
 */
public class TemplateEntity {

    private String  id;
    private String name;
    private String desc;
    private long downloadCount;
    private List<Action> actions;

    public TemplateEntity(String  id, String name, String desc, long downloadCount) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.downloadCount = downloadCount;
    }

    public TemplateEntity(String id, String name, String desc, long downloadCount, List<Action> actions) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.downloadCount = downloadCount;
        this.actions = actions;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(long downloadCount) {
        this.downloadCount = downloadCount;
    }
}
