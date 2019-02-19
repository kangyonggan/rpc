package com.kangyonggan.rpc.util;

import com.kangyonggan.rpc.core.RpcInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2019-02-19
 */
public final class InterceptorUtil {

    private static Map<String, RpcInterceptor> interceptorMap = new HashMap<>();

    private InterceptorUtil() {
    }

    public static RpcInterceptor get(String key) throws Exception {
        RpcInterceptor interceptor = interceptorMap.get(key);
        if (interceptor == null) {
            synchronized (interceptorMap) {
                interceptor = interceptorMap.get(key);
                if (interceptor == null) {
                    interceptor = (RpcInterceptor) Class.forName(key).newInstance();
                    interceptorMap.put(key, interceptor);
                }
            }
        }

        return interceptor;
    }

}
