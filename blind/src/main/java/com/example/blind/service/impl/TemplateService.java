package com.example.blind.service.impl;

import com.example.blind.dao.ITemplateDao;
import com.example.blind.model.Template;
import com.example.blind.service.ITemplateService;
import com.example.blind.vo.AppVO;
import com.example.blind.vo.TemplateListItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 10:29 AM 2019/7/13
 */
@Component
public class TemplateService implements ITemplateService {

    @Autowired
    ITemplateDao templateDao;

    @Override
    public AppVO getTemplates(long appId) {
        List<Template> templates = templateDao.getAppTemplate(appId);
        List<TemplateListItemVo> items = new ArrayList<>();
        long count = 0;
        String appName = "";
        if (templates.size()>0)
            appName = templates.get(0).getAppName();
        for(Template template: templates){
            items.add(new TemplateListItemVo(template));
            count += template.getDownloadCount();
        }
        AppVO app = new AppVO();
        app.setAppId(appId);
        app.setApp(appName);
        app.setTemplates(items);
        app.setTotalDownloadCount(count);
        return app;
    }


    @Override
    public List<AppVO> getAll() {
        List<Template> templates = templateDao.getAll();
        return getApps(templates);
    }

    private List<AppVO> getApps(List<Template> templates){
        List<AppVO> vos = new LinkedList<>();

        Map<String, AppVO> map = new HashMap<>();
        for(Template template: templates){
            if (!map.containsKey(template.getAppName())){
                AppVO vo = new AppVO();
                vo.setApp(template.getAppName());
                vo.setAppId(template.getAppId());
                map.put(template.getAppName(), vo);
            }
            AppVO vo = map.get(template.getAppName());
            List<TemplateListItemVo> items = vo.getTemplates();
            items.add(new TemplateListItemVo(template));
        }
        vos.addAll(map.values());
        return vos;
    }

    @Override
    public List<AppVO> match(String keyword) {
        List<Template> templates = templateDao.getAll().stream().filter(t -> t.getAppName().contains(keyword)).collect(Collectors.toList());
        return getApps(templates);
    }
}
