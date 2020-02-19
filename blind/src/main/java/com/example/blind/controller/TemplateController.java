package com.example.blind.controller;

import com.example.blind.service.ITemplateService;
import com.example.blind.vo.AppVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 10:28 AM 2019/7/13
 */
@RestController
public class TemplateController {

    @Autowired
    ITemplateService templateService;

    @GetMapping("/get")
    public AppVO getTemplate(@RequestParam("appId") long appId){
        return templateService.getTemplates(appId);
    }

    @GetMapping("/all")
    public List<AppVO> getAll(){
        return templateService.getAll();
    }

    @GetMapping("/query")
    public List<AppVO> getApps(@RequestParam("keyword") String keyword){
        return templateService.match(keyword);
    }
}
