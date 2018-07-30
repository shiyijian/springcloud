package com.web.cloud.feign.util;

import com.web.cloud.common.constant.ResponseCode;
import com.web.cloud.common.constant.UserAuthLevel;
import com.web.cloud.common.model.dto.BaseDTO;
import com.web.cloud.common.response.Response;
import com.web.cloud.feign.response.MopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public class MopApiUtil {

    private static final Logger log = LoggerFactory.getLogger(MopApiUtil.class);

    public static <T> MopResponse<T> transferResp(Response<T> resp, UserAuthLevel userAuthLevel) {
        if (resp.getCode() != ResponseCode.RESPONSE_SUCCESS.getCode()) {
            return new MopResponse(resp.getCode(), resp.getMessage());
        } else {
            /*if (resp.getModule() != null && (resp.getModule() instanceof BaseDTO)) {
                filterDataForSecurity((BaseDTO) resp.getModule(), userAuthLevel);
            }*/
            if (resp.getModule() != null) {
                deepDownTree(resp.getModule(), userAuthLevel);
            }
            return new MopResponse<T>(resp.getModule());
        }
    }

    public static <T> MopResponse transferResp(Response<T> resp, String moduleName, UserAuthLevel userAuthLevel) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (resp.getCode() != ResponseCode.RESPONSE_SUCCESS.getCode()) {
            return new MopResponse(resp.getCode(), resp.getMessage());
        } else if (resp.getModule() != null) {
            if (resp.getModule() instanceof Collection) {
                map.put(moduleName, resp.getModule());
                map.put("total_count", resp.getTotalCount());
            } else if (resp.getModule() instanceof Map) {
                map = (Map<String, Object>) resp.getModule();
            } else if (resp.getModule() instanceof BaseDTO) {
                map.put(moduleName, resp.getModule());
            }
        }
        if (resp.getModule() != null) {
            deepDownTree(resp.getModule(), userAuthLevel);
        }
        return new MopResponse(map);
    }

    private static void deepDownTree(Object target, UserAuthLevel userAuthLevel) {
        if (target instanceof Collection) {
            for (Object colObj : (Collection) target) {
                deepDownTree(colObj, userAuthLevel);
            }
            return;
        } else if (target instanceof Map) {
            for (Object mapObj : ((Map) target).entrySet()) {
                deepDownTree(((Map.Entry) mapObj).getValue(), userAuthLevel);
            }
            return;
        }

        //递归过滤
        Map<String, Field> fieldMap = getAllDeclaredField(target);
        for (Field field : fieldMap.values()) {
            //抑制Java对其的检查
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(target);
                if (fieldValue != null
                        && ((fieldValue instanceof Collection) || (fieldValue instanceof Map) || (fieldValue instanceof BaseDTO))) {
                    deepDownTree(fieldValue, userAuthLevel);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("error to getFieldValue, filedName:{}", field.getName(), e);
            }
        }

        if (target instanceof BaseDTO) {
            filterDataForSecurity((BaseDTO) target, userAuthLevel);
        }
    }

    public static MopResponse getResponse(Object obj) {
        return new MopResponse(obj);
    }

    public static MopResponse getResponse(Object obj,UserAuthLevel userAuthLevel) {
        if (obj != null) {
            deepDownTree(obj, userAuthLevel);
        }
        return new MopResponse(obj);
    }

    public static MopResponse getResponse(ResponseCode resp) {
        return new MopResponse(resp.getCode(), resp.getComment());
    }

    public static MopResponse getResponse(ResponseCode resp, String comment) {
        return new MopResponse(resp.getCode(), comment);
    }

    public static MopResponse getResponse(int code, String comment) {
        return new MopResponse(code, comment);
    }

    /**
     * 数据安全过滤
     *
     * @param baseDTO
     * @param userAuthLevel
     */
    public static void filterDataForSecurity(BaseDTO baseDTO, UserAuthLevel userAuthLevel) {
        List<String> filterFieldList = new ArrayList<String>();
        //普通用户（包括未登录与已登录）
        if (userAuthLevel.getValue() <= UserAuthLevel.LEVEL_USER.getValue()) {
            //限制性字段不对普通用户开放
            if (baseDTO.getLimitField() != null) {
                filterFieldList.addAll(Arrays.asList(baseDTO.getLimitField()));
            }
        }

        //保密性数据不对外部任何用户开放
        if (baseDTO.getSecretField() != null) {
            filterFieldList.addAll(Arrays.asList(baseDTO.getSecretField()));
        }

        for (String fieldName : filterFieldList) {
            try {
                setFieldValue(baseDTO, fieldName, null);
            } catch (Throwable throwable) {
                log.error("error to filterDataForSecurity, fieldName:{}", fieldName, throwable);
            }
        }
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param baseDTO   : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */

    private static Field getDeclaredField(BaseDTO baseDTO, String fieldName) {
        Map<String, Field> fieldMap = getAllDeclaredField(baseDTO);
        Field field = fieldMap.get(fieldName);
        return field;
    }

    /**
     * 获取指定dto对象的所有声明的属性（包括父类的属性）
     *
     * @param baseDTO
     * @return
     */
    private static Map<String, Field> getAllDeclaredField(Object baseDTO) {
        Map<String, Field> fieldMap = new HashMap<String, Field>();
        Class<?> clazz = baseDTO.getClass();
        //循环向上转型, 获取父类的 DeclaredField
        while (clazz != Object.class) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    fieldMap.put(field.getName(), field);
                }
            } catch (Exception ignore) {
            }
            clazz = clazz.getSuperclass();
        }

        return fieldMap;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过setter
     *
     * @param baseDTO   : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */
    private static void setFieldValue(BaseDTO baseDTO, String fieldName, Object value) {
        Field field = getDeclaredField(baseDTO, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //将baseDTO中field所代表的值设置为value
            field.set(baseDTO, value);
        } catch (Exception e) {
            log.error("error to setFieldValue, filedName:{}", fieldName, e);
        }

    }
}
