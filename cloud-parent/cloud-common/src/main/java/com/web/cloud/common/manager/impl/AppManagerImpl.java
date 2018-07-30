package com.web.cloud.common.manager.impl;

import com.google.common.collect.Maps;
import com.web.cloud.common.manager.AppManager;
import com.web.cloud.common.model.dto.AppInfoDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppManagerImpl implements AppManager {

    private static Map<String,AppInfoDTO> appInfoDTOMap = Maps.newHashMap();

    static {
        AppInfoDTO iosApp = new AppInfoDTO();
        iosApp.setId(1l);
        iosApp.setBizCode("jmxy");
        iosApp.setAppType(1);
        iosApp.setAppKey("8696546d0123694a8de3c46f72c1390b");
        iosApp.setAppPwd("28e28dd5ccd864d6b92afa2de3edaab8");
        iosApp.setAppName("iosApp");
        appInfoDTOMap.put("8696546d0123694a8de3c46f72c1390b",iosApp);

        AppInfoDTO androidApp = new AppInfoDTO();
        androidApp.setId(2l);
        androidApp.setBizCode("jmxy");
        androidApp.setAppType(2);
        androidApp.setAppKey("ad0055d0607d96f7985aea19d375aeb6");
        androidApp.setAppPwd("40daa117d0027f71c6b9c23c1de8ba2f");
        androidApp.setAppName("androidApp");
        appInfoDTOMap.put("ad0055d0607d96f7985aea19d375aeb6",androidApp);

        AppInfoDTO h5App = new AppInfoDTO();
        h5App.setId(3l);
        h5App.setBizCode("jmxy");
        h5App.setAppType(3);
        h5App.setAppKey("98764401ba85ebf61100f837c93bdb28");
        h5App.setAppPwd("2e327c309fe059a707c0ef11fb5f08f6");
        h5App.setAppName("H5APP");
        appInfoDTOMap.put("98764401ba85ebf61100f837c93bdb28",h5App);

        AppInfoDTO adminApp = new AppInfoDTO();
        adminApp.setId(4l);
        adminApp.setBizCode("jmxy");
        adminApp.setAppType(4);
        adminApp.setAppKey("e83a211ec1645e51c84506c065e3379b");
        adminApp.setAppPwd("b0051dcb001b3f684014f5921da23c43");
        adminApp.setAppName("manager");
        appInfoDTOMap.put("e83a211ec1645e51c84506c065e3379b",adminApp);

        AppInfoDTO enterpriseApp = new AppInfoDTO();
        enterpriseApp.setId(5l);
        enterpriseApp.setBizCode("jmxy");
        enterpriseApp.setAppType(5);
        enterpriseApp.setAppKey("1c32a226137d42bbbff37881b067d8d8");
        enterpriseApp.setAppPwd("ca7826101b284cf19694ffa5a2512703");
        enterpriseApp.setAppName("enterprise");
        appInfoDTOMap.put("1c32a226137d42bbbff37881b067d8d8",enterpriseApp);
    }

    @Override
    public AppInfoDTO getAppInfo(String appKey) {
        return appInfoDTOMap.containsKey(appKey)?appInfoDTOMap.get(appKey):null;
    }
}
