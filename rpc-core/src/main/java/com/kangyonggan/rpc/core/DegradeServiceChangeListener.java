package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.util.ServiceDegradeUtil;
import org.I0Itec.zkclient.IZkChildListener;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2019-02-19
 */
public class DegradeServiceChangeListener implements IZkChildListener {

    private Logger logger = Logger.getLogger(DegradeServiceChangeListener.class);

    @Override
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
        logger.info("监听到降级服务变化，parentPath=" + parentPath);
        ServiceDegradeUtil.getDegradeServices();
    }
}
