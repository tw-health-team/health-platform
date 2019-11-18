package com.theus.health.core.util;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangwei
 * @date 2018-07-12 15:18
 * Specification : 文档说明
 */
public class BaseConverter<DO, VO> {
    private static final Logger logger = LoggerFactory.getLogger(BaseConverter.class);

    /**
     * 单个对象转换
     */
    public VO convert(DO from, Class<VO> clazz) {
        if (from == null) {
            return null;
        }
        VO to = null;
        try {
            to = clazz.newInstance();
        } catch (Exception e) {
            logger.error("初始化{}对象失败。", clazz, e);
        }
        convert(from, to);
        return to;
    }

    /**
     * 批量对象转换
     */
    public List<VO> convert(List<DO> fromList, Class<VO> clazz) {
        List<VO> toList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(fromList)) {
            for (DO from : fromList) {
                toList.add(convert(from, clazz));
            }
        }
        return toList;
    }

    /**
     * 属性拷贝方法，有特殊需求时子类覆写此方法
     */
    private void convert(DO from, VO to) {
        BeanUtils.copyProperties(from, to);
    }
}
