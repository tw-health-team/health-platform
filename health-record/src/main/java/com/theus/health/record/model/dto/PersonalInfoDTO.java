package com.theus.health.record.model.dto;

import lombok.Data;

import java.sql.Date;

/**
 * @author tangwei
 * @date 2020-03-25 15:14
 */
@Data
public class PersonalInfoDTO {

    /**
     * 个人档案标识号
     */
    private String personalId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份证件类别
     */
    private String cardType;

    /**
     * 身份证件号码
     */
    private String cardNum;

    /**
     *身份证号
     */
    private String idCard;

    /**
     * 工作单位
     */
    private String companyName;

    /**
     * 本人电话
     */
    private String cellPhone;

    /**
     * 户籍地址-省（自治区、直辖市）编码
     */
    private String householdProvinceCode;

    /**
     * 户籍地址-省（自治区、直辖市）名称
     */
    private String householdProvinceName;

    /**
     * 户籍地址-市（地区）编码
     */
    private String householdCityCode;

    /**
     * 户籍地址-市（地区）名称
     */
    private String householdCityName;

    /**
     * 户籍地址-县（区）编码
     */
    private String householdCountyCode;

    /**
     * 户籍地址-县（区）名称
     */
    private String householdCountyName;

    /**
     * 户籍地址-乡（镇、街道）编码
     */
    private String householdTownCode;

    /**
     * 户籍地址-乡（镇、街道）名称
     */
    private String householdTownName;

    /**
     * 户籍地址-村、居委编码
     */
    private String householdCommitteeCode;

    /**
     * 户籍地址-村、居委名称
     */
    private String householdCommitteeName;

    /**
     * 户籍弄
     */
    private String householdNong;

    /**
     * 户籍号
     */
    private String householdHao;

    /**
     * 户籍室
     */
    private String householdShi;

    /**
     * 户籍地址-详细地址
     */
    private String householdAddressDetail;

    /**
     * 户籍地址
     */
    private String householdAddress;

    /**
     * 户籍区划编码
     */
    private String householdCode;

    /**
     * 居住地址-省（自治区、直辖市）编码
     */
    private String residenceProvinceCode;

    /**
     * 居住地址-省（自治区、直辖市）名称
     */
    private String residenceProvinceName;

    /**
     * 居住地址-市（地区、州）编码
     */
    private String residenceCityCode;

    /**
     * 居住地址-市（地区、州）名称
     */
    private String residenceCityName;

    /**
     * 居住地址-县（区）编码
     */
    private String residenceCountyCode;

    /**
     * 居住地址-县（区）名称
     */
    private String residenceCountyName;

    /**
     * 居住地址-乡（镇、街道）编码
     */
    private String residenceTownCode;

    /**
     * 居住地址-乡（镇、街道）名称
     */
    private String residenceTownName;

    /**
     * 居住地址-村、居委编码
     */
    private String residenceCommitteeCode;

    /**
     * 居住地址-村、居委名称
     */
    private String residenceCommitteeName;

    /**
     * 居住弄
     */
    private String residenceNong;

    /**
     * 居住号
     */
    private String residenceHao;

    /**
     * 居住室
     */
    private String residenceShi;

    /**
     * 居住地址-详细地址
     */
    private String residenceAddressDetail;

    /**
     * 居住地址
     */
    private String residenceAddress;

    /**
     * 居住区划编码
     */
    private String residenceCode;

    /**
     * 联系人姓名
     */
    private String contactsName;

    /**
     * 联系人电话
     */
    private String contactsPhone;

    /**
     * 常住类型
     */
    private String permanentType;

    /**
     * 民族代码
     */
    private String nationCode;

    /**
     * 民族名称
     */
    private String nationName;

    /**
     * 少数民族编码
     */
    private String minorityCode;

    /**
     * 少数民族名称
     */
    private String minorityName;

    /**
     * ABO血型
     */
    private String bloodAbo;

    /**
     * RH血型
     */
    private String bloodRh;

    /**
     * 文化程度代码
     */
    private String educationCode;

    /**
     * 文化程度名称
     */
    private String educationName;

    /**
     * 职业代码
     */
    private String professionCode;

    /**
     * 职业名称
     */
    private String professionName;

    /**
     * 职业其他
     */
    private String professionOther;

    /**
     * 婚姻状况代码
     */
    private String marriageCode;

    /**
     * 婚姻状况名称
     */
    private String marriageName;

    /**
     * 医疗费用支付方式代码
     */
    private String medicalPaymentCode;

    /**
     * 医疗费用支付方式名称
     */
    private String medicalPaymentName;

    /**
     * 医疗费用支付方式其他
     */
    private String medicalPaymentOther;

    /**
     * 药物过敏代码
     */
    private String drugAllergyCode;

    /**
     * 药物过敏名称
     */
    private String drugAllergyName;

    /**
     * 药物过敏其他
     */
    private String drugAllergyOther;

    /**
     * 暴露史代码
     */
    private String exposeHistoryCode;

    /**
     * 暴露史名称
     */
    private String exposeHistoryName;

    /**
     * 暴露(化学)
     */
    private String exposeChemistry;

    /**
     * 暴露(毒物)
     */
    private String exposePoison;

    /**
     * 暴露(射线)
     */
    private String exposeRay;

    /**
     * 父亲病史代码
     */
    private String fatherDiseaseCode;

    /**
     * 父亲病史名称
     */
    private String fatherDiseaseName;

    /**
     * 父亲病史其他
     */
    private String fatherDiseaseOther;

    /**
     * 母亲病史代码
     */
    private String motherDiseaseCode;

    /**
     * 母亲病史名称
     */
    private String motherDiseaseName;

    /**
     * 母亲病史其他
     */
    private String motherDiseaseOther;

    /**
     * 兄弟姐妹病史代码
     */
    private String siblingDiseaseCode;

    /**
     * 兄弟姐妹病史名称
     */
    private String siblingDiseaseName;

    /**
     * 兄弟姐妹病史其他
     */
    private String siblingDiseaseOther;

    /**
     * 子女病史代码
     */
    private String childrenDiseaseCode;

    /**
     * 子女病史名称
     */
    private String childrenDiseaseName;

    /**
     * 子女病史其他
     */
    private String childrenDiseaseOther;

    /**
     * 是否有遗传病史
     */
    private String hasGeneticDisease;

    /**
     * 遗传病名
     */
    private String geneticDiseaseName;

    /**
     * 残疾代码
     */
    private String disabilityCode;

    /**
     * 残疾名称
     */
    private String disabilityName;

    /**
     * 残疾其他
     */
    private String disabilityOther;

    /**
     * 残疾证号
     */
    private String disabilityCardNum;

    /**
     * 厨房排风设施
     */
    private String kitchenType;

    /**
     * 燃料类型
     */
    private String fuelType;

    /**
     * 饮水
     */
    private String drinkWater;

    /**
     * 厕所
     */
    private String toiletType;

    /**
     * 禽畜栏
     */
    private String livestockType;

    /**
     * 管理状态
     */
    private String controlState;

    /**
     * 失访原因代码
     */
    private String lostReasonCode;

    /**
     * 失访原因名称
     */
    private String lostReasonName;

    /**
     * 死亡日期
     */
    private Date deathDate;

    /**
     * 死亡原因
     */
    private String deathCause;

    /**
     * 业务管理机构代码
     */
    private String manageOrganCode;

    /**
     * 业务管理机构名称
     */
    private String manageOrganName;
}
