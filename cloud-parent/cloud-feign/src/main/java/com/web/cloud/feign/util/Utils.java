package com.web.cloud.feign.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {

    public static String genPassword(String str){
        return DigestUtils.md5Hex("jm"+DigestUtils.md5Hex("xy"+str));
    }

    public static String genAuthTokenCacheKeyForUser(long userId) {
        return "authToken_user_" + userId;
    }

    public static String genAuthTokenCacheKeyForAdmin(long userId) {
        return "authToken_admin_" + userId;
    }

    public static String genAuthTokenCacheKeyForEnterpriseAdmin(long userId){
        return "authToken_enterprise_admin_" + userId;
    }
}
