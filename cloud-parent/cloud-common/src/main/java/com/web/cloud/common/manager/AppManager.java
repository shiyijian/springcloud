package com.web.cloud.common.manager;

import com.web.cloud.common.model.dto.AppInfoDTO;

public interface AppManager {

    AppInfoDTO getAppInfo(String appKey);
}
