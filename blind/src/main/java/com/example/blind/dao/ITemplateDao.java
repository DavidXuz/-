package com.example.blind.dao;

import com.example.blind.model.Template;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 9:28 AM 2019/7/13
 */
@Component
public interface ITemplateDao {

    List<Template> getAppTemplate(long appId);

    List<Template> getAll();
}
