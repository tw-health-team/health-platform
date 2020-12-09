package com.theus.health.record.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.core.util.HDateUtil;
import com.theus.health.core.util.IdCardUtil;
import com.theus.health.record.constants.DictConstants;
import com.theus.health.record.model.po.EhrPersonalInfo;

import java.sql.Date;

/**
 * 个人基本信息工具类
 *
 * @author tangwei
 * @date 2020-04-09 14:44
 */
public class PersonalInfoUtil {
    /**
     * 完善基本信息
     *
     * @param ehrPersonalInfo 个人基本信息
     */
    public static void completeInfo(EhrPersonalInfo ehrPersonalInfo) {
        // 身份证类型
        String idCardType = DictConstants.CardType.ID_CARD.getValue();
        String idCard = ehrPersonalInfo.getIdCard();
        if (StrUtil.isNotBlank(idCard)) {
            ehrPersonalInfo.setCardType(DictConstants.CardType.ID_CARD.getValue());
            ehrPersonalInfo.setCardNum(idCard);
        } else {
            String cardType = ehrPersonalInfo.getCardType();
            // 证件类型是身份证
            if (idCardType.equals(cardType)) {
                idCard = ehrPersonalInfo.getCardNum();
                ehrPersonalInfo.setIdCard(idCard);
            }
        }
        // 通过身份证号获取出生日期
        java.util.Date birthDay = IdCardUtil.getBirthByIdCard(idCard);
        if (birthDay != null) {
            ehrPersonalInfo.setBirthday(HDateUtil.toSqlDate(birthDay));
        }
        // 通过身份证号获取性别
        ehrPersonalInfo.setGender(IdCardUtil.getGenderByIdCard(idCard));
        // 拼接完整户籍、居住地址
        getFullAddressName(ehrPersonalInfo);
        // 获取户籍、居住地址的区划编码
        getAreaCode(ehrPersonalInfo);

        // 死亡原因代码不为空 则管理状态为继续随访，否则为失访
        if (StrUtil.isNotBlank(ehrPersonalInfo.getLostReasonCode())) {
            // 失访
            ehrPersonalInfo.setControlState(DictConstants.ControlState.LOSE_FOLLOWUP.getValue());
        } else {
            // 继续随访
            ehrPersonalInfo.setControlState(DictConstants.ControlState.CONTINUE_FOLLOWUP.getValue());
        }
    }

    /**
     * 获取完整地址信息
     *
     * @param ehrPersonalInfo 个人基本信息
     */
    private static void getFullAddressName(EhrPersonalInfo ehrPersonalInfo) {
        String fullAddressName;
        // 户籍地址
        String addressDetail = ehrPersonalInfo.getHouseholdAddressDetail() == null ? "" : ehrPersonalInfo.getHouseholdAddressDetail();
        fullAddressName = getFullAreaName(ehrPersonalInfo.getHouseholdProvinceName(), ehrPersonalInfo.getHouseholdCityName(),
                ehrPersonalInfo.getHouseholdCountyName(), ehrPersonalInfo.getHouseholdTownName(),
                ehrPersonalInfo.getHouseholdCommitteeName()) + addressDetail;
        ehrPersonalInfo.setHouseholdAddress(fullAddressName);

        // 居住地址
        fullAddressName = getFullAreaName(ehrPersonalInfo.getResidenceProvinceName(), ehrPersonalInfo.getResidenceCityName(),
                ehrPersonalInfo.getResidenceCountyName(), ehrPersonalInfo.getResidenceTownName(),
                ehrPersonalInfo.getResidenceCommitteeName()) + addressDetail;
        ehrPersonalInfo.setResidenceAddress(fullAddressName);
    }

    /**
     * 获取完整行政区划名
     *
     * @param provinceName  省名称
     * @param cityName      市名称
     * @param countyName    区县名称
     * @param townName      街道名称
     * @param committeeName 居委名称
     * @return 完整名称
     */
    private static String getFullAreaName(String provinceName, String cityName, String countyName, String townName, String committeeName) {
        String fullAreaName;
        provinceName = provinceName == null ? "" : provinceName;
        cityName = cityName == null ? "" : cityName;
        countyName = countyName == null ? "" : countyName;
        townName = townName == null ? "" : townName;
        committeeName = committeeName == null ? "" : committeeName;
        // 属于直辖市则不拼接地市名称
        if (ArrayUtil.contains(SysConstants.Area.provLevelMunicipality, cityName)) {
            fullAreaName = cityName + countyName + townName + committeeName;
        } else {
            fullAreaName = provinceName + cityName + countyName + townName + committeeName;
        }
        return fullAreaName;
    }

    /**
     * 根据地址信息获取所在区划编码
     *
     * @param ehrPersonalInfo 个人基本信息
     */
    private static void getAreaCode(EhrPersonalInfo ehrPersonalInfo) {
        ehrPersonalInfo.setHouseholdCode(getAreaCode(ehrPersonalInfo.getHouseholdProvinceCode(), ehrPersonalInfo.getHouseholdCityCode(),
                ehrPersonalInfo.getHouseholdCountyCode(), ehrPersonalInfo.getHouseholdTownCode(),
                ehrPersonalInfo.getHouseholdCommitteeCode()));

        ehrPersonalInfo.setResidenceCode(getAreaCode(ehrPersonalInfo.getResidenceProvinceCode(), ehrPersonalInfo.getResidenceCityCode(),
                ehrPersonalInfo.getResidenceCountyCode(), ehrPersonalInfo.getResidenceTownCode(),
                ehrPersonalInfo.getResidenceCommitteeCode()));
    }

    /**
     * 获取区划编码
     *
     * @param provinceCode  省编码
     * @param cityCode      地市编码
     * @param countyCode    区县编码
     * @param townCode      街道编码
     * @param committeeCode 居委编码
     * @return 区划编码
     */
    private static String getAreaCode(String provinceCode, String cityCode, String countyCode, String townCode, String committeeCode) {
        String areaCode = "";
        if (StrUtil.isNotEmpty(committeeCode)) {
            areaCode = committeeCode;
        } else if (StrUtil.isNotEmpty(townCode)) {
            areaCode = townCode;
        } else if (StrUtil.isNotEmpty(countyCode)) {
            areaCode = countyCode;
        } else if (StrUtil.isNotEmpty(cityCode)) {
            areaCode = cityCode;
        } else if (StrUtil.isNotEmpty(provinceCode)) {
            areaCode = provinceCode;
        }
        return areaCode;
    }
}
