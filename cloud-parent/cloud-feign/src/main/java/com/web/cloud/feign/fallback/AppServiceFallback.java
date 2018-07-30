package com.web.cloud.feign.fallback;

import com.web.cloud.common.model.dto.AppInfoDTO;
import com.web.cloud.feign.service.AppService;
import org.springframework.stereotype.Component;

@Component
public class AppServiceFallback implements AppService {

    @Override
    public AppInfoDTO getAppInfoByAppKey(String appKey) {
        return null;
    }
}
