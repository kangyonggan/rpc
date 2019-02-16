package com.kangyonggan.rpc.xsd;

import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * 从xml标签解析为pojo
 *
 * @author kangyonggan
 * @since 2019-02-13
 */
public class SimpleBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    private Class<?> pojoClass;

    SimpleBeanDefinitionParser(Class<?> pojoClass) {
        this.pojoClass = pojoClass;
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return pojoClass;
    }

}
