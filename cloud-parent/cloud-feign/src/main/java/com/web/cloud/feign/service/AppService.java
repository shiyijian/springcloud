package com.web.cloud.feign.service;

import com.web.cloud.common.model.dto.AppInfoDTO;
import com.web.cloud.feign.fallback.AppServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cloud-provider",fallback = AppServiceFallback.class)
public interface AppService {

    @RequestMapping(value = "/getAppInfoByAppKey",method = RequestMethod.GET)
    AppInfoDTO getAppInfoByAppKey(@RequestParam("appKey") String appKey);
}
