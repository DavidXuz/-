package com.talkover.model;

import java.util.List;

/**
 * Created by heleninsa on 2019-07-13.
 *
 * @author heleninsa
 */
public class AppEntity {

    private long appId;
    private String app;
    private long totalDownloadCount;
    private List<TemplateEntity> templates;

    public AppEntity(long appId, String app, long totalDownloadCount, List<TemplateEntity> templates) {
        this.appId = appId;
        this.app = app;
        this.totalDownloadCount = totalDownloadCount;
        this.templates = templates;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public long getTotalDownloadCount() {
        return totalDownloadCount;
    }

    public void setTotalDownloadCount(long totalDownloadCount) {
        this.totalDownloadCount = totalDownloadCount;
    }

    public List<TemplateEntity> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateEntity> templates) {
        this.templates = templates;
    }
}
