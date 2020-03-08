package com.theus.health.base.common.validator;

import com.theus.health.base.common.constants.SysConstants;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 行政区划位数验证实现类
 * @author tangwei
 * @date 2020-02-28 21:14
 */
public class AreaLengthConstraintValidator implements ConstraintValidator<AreaLengthConstraint, String> {
    @Override
    public void initialize(AreaLengthConstraint constraint) {
        //启动时执行
    }

    /**
     * 自定义校验逻辑
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        boolean bResult = false;
        try {
            int len = id.length();
            bResult = len== SysConstants.Area.PROVINCE_CODE_LEN || len==SysConstants.Area.CITY_CODE_LEN
                    || len==SysConstants.Area.DISTRICT_CODE_LEN || len==SysConstants.Area.TOWN_CODE_LEN
                    || len==SysConstants.Area.COMMITTEE_CODE_LEN;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bResult;
    }
}
