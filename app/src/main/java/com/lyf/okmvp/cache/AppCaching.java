package com.lyf.okmvp.cache;

import java.io.IOException;

import framework.cache.BaseAppCaching;
import framework.cache.ISystemConfig;
import framework.cache.IUserInfo;

/**
 * @Author Lyf
 * @CreateTime 2018/3/1
 * @Description An AppCaching class is caching data of app in SharePreference.
 **/
public class AppCaching extends BaseAppCaching {

    public static void saveUserInfo(IUserInfo userInfo) {
        getAppCaching().saveAppCaching(userInfo);
    }

    public static void saveSystemConfig(ISystemConfig systemConfig) {
        getAppCaching().saveAppCaching(systemConfig);
    }

    public static IUserInfo getUserInfo() {
        return getAppCaching().getAppCaching(IUserInfo.class);
    }

    public static ISystemConfig getSystemConfig() {
        return getAppCaching().getAppCaching(ISystemConfig.class);
    }

    public static <T> void saveData(String key, T tData) {
        getAppCaching().saveAppCaching(key, tData);
    }

    public static <T> T getData(String key, Class<T> tClass) {
        return getAppCaching().getAppCaching(key, tClass);
    }

    @Override
    protected String encryptCaching(String json) throws IOException {
        return super.encryptCaching(json);
    }

    @Override
    protected String decryptCaching(String json) {
        return super.decryptCaching(json);
    }
}
