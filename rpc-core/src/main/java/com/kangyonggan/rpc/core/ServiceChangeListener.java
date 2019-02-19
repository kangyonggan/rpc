package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.util.RefrenceUtil;
import org.I0Itec.zkclient.IZkChildListener;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 服务变化监听
 *
 * @author kangyonggan
 * @since 2019-02-19
 */
public class ServiceChangeListener implements IZkChildListener {

    private Logger logger = Logger.getLogger(ServiceChangeListener.class);

    private String refrenceName;

    public ServiceChangeListener(String refrenceName) {
        this.refrenceName = refrenceName;
    }

    @Override
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
        logger.info("监听到服务变化，refrenceName=" + refrenceName);
        RefrenceUtil.get(refrenceName).getRefrences();
    }
}
