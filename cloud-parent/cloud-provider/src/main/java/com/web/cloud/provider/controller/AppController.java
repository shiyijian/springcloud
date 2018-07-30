package com.web.cloud.provider.controller;

import com.web.cloud.common.manager.AppManager;
import com.web.cloud.common.model.dto.AppInfoDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AppController {

    @Resource
    private AppManager appManager;

    @RequestMapping(value = "/getAppInfoByAppKey",method = RequestMethod.GET)
    public AppInfoDTO getAppInfoByAppKey(String appKey){
        return appManager.getAppInfo(appKey);
    }
}
