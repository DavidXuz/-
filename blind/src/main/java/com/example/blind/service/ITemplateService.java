package com.example.blind.service;

import com.example.blind.vo.AppVO;

import java.util.List;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 10:29 AM 2019/7/13
 */
public interface ITemplateService {

    AppVO getTemplates(long appId);

    List<AppVO> getAll();

    List<AppVO> match(String keyword);
}
