package com.kangyonggan.rpc.util;

import com.kangyonggan.rpc.pojo.Refrence;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2019-02-19
 */
public final class RefrenceUtil {

    private static Map<String, Refrence> refrenceMap = new HashMap<>();

    private RefrenceUtil() {

    }

    public static void put(Refrence refrence) {
        refrenceMap.put(refrence.getName(), refrence);
    }

    public static Refrence get(String refrenceName) throws Exception {
        Refrence refrence = refrenceMap.get(refrenceName);
        if (refrence == null) {
            synchronized (refrenceMap) {
                refrence = refrenceMap.get(refrenceName);
                if (refrence == null) {
                    refrence = new Refrence();
                    refrence.setName(refrenceName);
                    refrence.init();
                    refrenceMap.put(refrenceName, refrence);
                }
            }
        }

        return refrence;
    }
}
