package com.example.blind.dao.impl;

import com.example.blind.dao.ITemplateDao;
import com.example.blind.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 9:43 AM 2019/7/13
 */
@Component
public class TemplateDao implements ITemplateDao {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Template> getAppTemplate(long appId) {
        Criteria criteria = Criteria.where(Template.APPID).is(appId);
        return mongoTemplate.find(new Query(criteria), Template.class);
    }

    @Override
    public List<Template> getAll() {
        return mongoTemplate.find(new Query(), Template.class);
    }
}
