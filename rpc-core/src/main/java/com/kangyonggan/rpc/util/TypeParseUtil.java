package com.kangyonggan.rpc.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换工具
 *
 * @author kangyonggan
 * @since 2019-02-15
 */
public final class TypeParseUtil {

    private TypeParseUtil() {
    }

    /**
     * 转类型字符串到类型对象
     *
     * @param types
     * @return
     * @throws Throwable
     */
    public static Map<String, Object> parseTypeString2Class(String[] types, Object[] args) throws Throwable {
        Map<String, Object> result = new HashMap<>(types.length);
        Class<?>[] classTypes = new Class<?>[types.length];
        for (int i = 0; i < types.length; i++) {
            if ("byte".equals(types[i])) {
                classTypes[i] = byte.class;
                if (null == args[i]) {
                    args[i] = getBasicTypeDefaultValue(byte.class);
                }
            } else if ("short".equals(types[i])) {
                classTypes[i] = short.class;
                if (null == args[i]) {
                    args[i] = getBasicTypeDefaultValue(short.class);
                }
            } else if ("int".equals(types[i])) {
                classTypes[i] = int.class;
                if (null == args[i]) {
                    args[i] = getBasicTypeDefaultValue(int.class);
                }
            } else if ("long".equals(types[i])) {
                classTypes[i] = long.class;
                if (null == args[i]) {
                    args[i] = getBasicTypeDefaultValue(long.class);
                }
            } else if ("float".equals(types[i])) {
                classTypes[i] = float.class;
                if (null == args[i]) {
                    args[i] = getBasicTypeDefaultValue(float.class);
                }
            } else if ("double".equals(types[i])) {
                classTypes[i] = double.class;
                if (null == args[i]) {
                    args[i] = getBasicTypeDefaultValue(double.class);
                }
            } else if ("boolean".equals(types[i])) {
                classTypes[i] = boolean.class;
                if (null == args[i]) {
                    args[i] = getBasicTypeDefaultValue(boolean.class);
                }
            } else if ("char".equals(types[i])) {
                classTypes[i] = char.class;
                if (null == args[i]) {
                    args[i] = getBasicTypeDefaultValue(char.class);
                }
            } else {
                classTypes[i] = Class.forName(types[i]);
            }
        }

        result.put("classTypes", classTypes);
        result.put("args", args);
        return result;
    }

    /**
     * 返回基础类型默认值
     *
     * @return
     */
    private static Object getBasicTypeDefaultValue(Class<?> type) {
        if (byte.class.equals(type)) {
            return 0;
        } else if (short.class.equals(type)) {
            return 0;
        } else if (int.class.equals(type)) {
            return 0;
        } else if (long.class.equals(type)) {
            return 0;
        } else if (float.class.equals(type)) {
            return 0;
        } else if (double.class.equals(type)) {
            return 0;
        } else if (boolean.class.equals(type)) {
            return 0;
        } else if (char.class.equals(type)) {
            return 0;
        }
        return null;
    }

}
