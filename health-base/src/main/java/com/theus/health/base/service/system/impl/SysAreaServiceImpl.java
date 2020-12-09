package com.theus.health.base.service.system.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.common.constants.DictConstants;
import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.base.mapper.system.SysAreaMapper;
import com.theus.health.base.model.dto.system.area.AreaDTO;
import com.theus.health.base.model.dto.system.area.FindAreaDTO;
import com.theus.health.base.model.po.system.SysArea;
import com.theus.health.base.model.vo.area.AreaTreeVO;
import com.theus.health.base.service.system.SysAreaService;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.core.util.BaseConverter;
import com.theus.health.core.util.ChinesePinyinUtil;
import com.theus.health.core.util.HFileUtil;
import com.theus.health.core.util.HStrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.theus.health.base.common.constants.DictConstants.AreaLevel.*;

/**
 * @author libin
 * @date 2019-12-12 15:00
 */
@Service
@Slf4j
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements SysAreaService {

    @Resource
    private SysAreaMapper sysAreaMapper;

    /**
     * 区划长度映射区划登记代码
     */
    private HashMap<Integer, String> lenToLevel = new HashMap<Integer, String>() {
        {
            put(2, PROVINCE.getValue());
            put(4, CITY.getValue());
            put(6, DISTRICT.getValue());
            put(9, DictConstants.AreaLevel.TOWN.getValue());
            put(12, DictConstants.AreaLevel.COMMITTEE.getValue());
        }
    };

    @Override
    public List<AreaTreeVO> findChildren(String id) {
        List<SysArea> children = sysAreaMapper.findChildren(id);
        return new BaseConverter<SysArea, AreaTreeVO>().convert(children, AreaTreeVO.class);
    }

    /**
     * 根据区划编码查询区划信息
     *
     * @param id 区划编码
     * @return 区划信息
     */
    @Override
    public AreaTreeVO findById(String id) {
        SysArea sysArea = this.getOne(new QueryWrapper<SysArea>().eq("id", id));
        AreaTreeVO areaTreeVO = new BaseConverter<SysArea, AreaTreeVO>().convert(sysArea, AreaTreeVO.class);
        this.createParentName(sysArea, areaTreeVO);
        return areaTreeVO;
    }

    /**
     * 根据区划编码查询父级区划信息
     *
     * @param id 区划编码
     * @return 区划信息
     */
    @Override
    public AreaTreeVO findParent(String id) {
        return this.findById(this.getParentId(id));
    }

    /**
     * 通过id获取上级id
     * @param id 区划编码
     * @return 上级区划编码
     */
    private String getParentId(String id) {
        int length = id.length();
        String parentId;
        int startIndex = SysConstants.STRING_START_INDEX;
        int proLen = SysConstants.Area.PROVINCE_CODE_LEN;
        // 区县代码长度
        int townLen = SysConstants.Area.TOWN_CODE_LEN;
        int districtLen = SysConstants.Area.DISTRICT_CODE_LEN;
        int cityLen = SysConstants.Area.CITY_CODE_LEN;
        if (length > townLen) {
            parentId = id.substring(startIndex, townLen);
        } else if (length > districtLen) {
            parentId = id.substring(startIndex, districtLen);
        } else if (length > cityLen) {
            parentId = id.substring(startIndex, cityLen);
        } else if (length > proLen) {
            parentId = id.substring(startIndex, proLen);
        } else {
            parentId = SysConstants.TOP_COMMON_CODE;
        }
        return parentId;
    }

    /**
     * 获取所有下级行政区划
     *
     * @param findAreaDTO 查询条件
     * @return 级行政区划列表
     */
    private List<AreaTreeVO> findAllNodes(FindAreaDTO findAreaDTO) {
        List<SysArea> children = sysAreaMapper.findAreas(findAreaDTO);
        return new BaseConverter<SysArea, AreaTreeVO>().convert(children, AreaTreeVO.class);
    }

    /**
     * 获取区划信息树形信息
     *
     * @param findAreaDTO 查询条件
     * @return 树形信息
     */
    @Override
    public List<AreaTreeVO> findTree(FindAreaDTO findAreaDTO) {
        List<AreaTreeVO> parent = new ArrayList<>();
        List<AreaTreeVO> areaTreeVOS = this.findAllNodes(findAreaDTO);
        if (areaTreeVOS != null && areaTreeVOS.size() > 0) {
            // 第一步：保存所有的id
            List<String> allID = new ArrayList<>();
            for (AreaTreeVO areaTreeVO : areaTreeVOS) {
                allID.add(areaTreeVO.getId());
            }
            // 第二步：遍历areaTreeVOS找出所有的根节点和非根节点
            List<AreaTreeVO> notParent = new ArrayList<>();
            for (AreaTreeVO areaTreeVO : areaTreeVOS) {
                if (!allID.contains(areaTreeVO.getParentId())) {
                    parent.add(areaTreeVO);
                } else {
                    notParent.add(areaTreeVO);
                }
            }
            // 第三步： 递归获取所有子节点
            if (parent.size() > 0) {
                for (AreaTreeVO areaTreeVO : parent) {
                    // 添加所有子级
                    areaTreeVO.setChildren(createTree(notParent, areaTreeVO.getId()));
                }
            }
        }
        return parent;
    }

    /**
     * 根据区划列表生成区划树
     *
     * @param areaList 有序区划列表
     * @return 区划树
     */
    private List<AreaTreeVO> createTree(List<AreaTreeVO> areaList, String parentId) {
        List<AreaTreeVO> areaTree = new ArrayList<>();
        if (areaList != null) {
            Iterator<AreaTreeVO> iterator = areaList.iterator();
            while (iterator.hasNext()) {
                AreaTreeVO area = iterator.next();
                // 得到子区划后跳出
                if (parentId.equals(area.getParentId())) {
                    areaTree.add(area);
                    // 从列表删除
                    iterator.remove();
                }
            }
            // 根据上级区划和区划列表生成区划树
            findChildren(areaTree, areaList);
        }
        return areaTree;
    }

    /**
     * 根据上级区划和区划列表生成区划树
     *
     * @param areaTree 上级区划list
     * @param areaList 区划列表
     */
    private void findChildren(List<AreaTreeVO> areaTree, List<AreaTreeVO> areaList) {
        // 循环上级区划
        for (AreaTreeVO superior : areaTree) {
            List<AreaTreeVO> children = new ArrayList<>();
            if (areaList != null) {
                // 迭代器循环区划列表
                Iterator<AreaTreeVO> iterator = areaList.iterator();
                while (iterator.hasNext()) {
                    AreaTreeVO area = iterator.next();
                    // 得到子区划后放入children中并从原列表中删除
                    if (superior.getId().equals(area.getParentId())) {
                        children.add(area);
                        // 从列表删除
                        iterator.remove();
                    }
                }
                // 根据上级区划和区划列表生成区划树
                findChildren(children, areaList);
                // 最终的子集树放入父级children集中
                superior.setChildren(children);
            }
        }
    }

    /**
     * 新增区划信息
     *
     * @param addDTO 新增数据集
     */
    @Override
    public void add(AreaDTO addDTO) {
        SysArea sysArea = this.getById(addDTO.getId());
        if (sysArea != null) {
            throw BusinessException.fail(String.format("已经存在编码为 %s 的区划信息", addDTO.getId()));
        }
        sysArea = new BaseConverter<AreaDTO, SysArea>().convert(addDTO, SysArea.class);
        // 根据名称生成拼音码
        this.createPinyin(sysArea);
        // 完善区划信息
        this.completeArea(sysArea);
        this.save(sysArea);
    }

    /**
     * 修改区划信息
     *
     * @param updateDTO 修改数据集
     */
    @Override
    public void update(AreaDTO updateDTO) {
        SysArea area = this.getById(updateDTO.getId());
        if (area == null) {
            throw BusinessException.fail(
                    String.format("更新失败，不存在编码为 %s 的区划信息", updateDTO.getId()));
        }
        boolean nameChanged = !area.getName().equals(updateDTO.getName());
        area = new BaseConverter<AreaDTO, SysArea>().convert(updateDTO, SysArea.class);
        // 名称改变 重新生成拼音码
        if (nameChanged) {
            // 根据名称生成拼音码
            this.createPinyin(area);
        }
        // 完善区划信息
        this.completeArea(area);
        try {
            this.updateById(area);
        } catch (BusinessException e) {
            throw BusinessException.fail(e.getMsg(), e);
        } catch (Exception e) {
            throw BusinessException.fail("区划信息更新失败", e);
        }
    }

    /**
     * 完善区划信息
     *
     * @param sysArea 行政区划
     */
    private void completeArea(SysArea sysArea) {
        // 区划级别代码
        String levelCode = this.getAreaLevel(sysArea.getId());
        sysArea.setLevelType(levelCode);
        // 获取上级行政区划
        SysArea parent = this.getById(this.getParentId(sysArea.getId()));
        // 获取ID路径
        sysArea.setEntirePath(parent.getEntirePath() + SysConstants.Area.CODE_SPLIT_CHAR + sysArea.getId());
        // 获取省、市、区县、街道、居委名称及其拼音码
        if (levelCode.equals(PROVINCE.getValue())) {
            // 省名称
            sysArea.setProvince(sysArea.getName());
            // 省简称
            sysArea.setProvinceShortName(sysArea.getShortName());
            // 省简拼
            sysArea.setProvinceSpelling(sysArea.getFullSpelling());
        } else {
            // 省获取上级
            sysArea.setProvince(parent.getProvince());
            sysArea.setProvinceShortName(parent.getShortName());
            sysArea.setProvinceSpelling(parent.getFullSpelling());
            if (levelCode.equals(CITY.getValue())) {
                sysArea.setCity(sysArea.getName());
                sysArea.setCityShortName(sysArea.getShortName());
                sysArea.setCitySpelling(sysArea.getFullSpelling());
            } else {
                // 市获取上级
                sysArea.setCity(parent.getCity());
                sysArea.setCityShortName(parent.getCityShortName());
                sysArea.setCitySpelling(parent.getCitySpelling());
                if (levelCode.equals(DISTRICT.getValue())) {
                    sysArea.setDistrict(sysArea.getName());
                    sysArea.setDistrictShortName(sysArea.getShortName());
                    sysArea.setDistrictSpelling(sysArea.getFullSpelling());
                } else {
                    // 区县获取上级
                    sysArea.setDistrict(parent.getDistrict());
                    sysArea.setDistrictShortName(parent.getDistrictShortName());
                    sysArea.setDistrictSpelling(parent.getDistrictSpelling());
                    if (levelCode.equals(DictConstants.AreaLevel.TOWN.getValue())) {
                        sysArea.setTown(sysArea.getName());
                        sysArea.setTownShortName(sysArea.getShortName());
                        sysArea.setTownSpelling(sysArea.getFullSpelling());
                    } else {
                        // 街道获取上级
                        sysArea.setTown(parent.getTown());
                        sysArea.setTownShortName(parent.getTownShortName());
                        sysArea.setTownSpelling(parent.getTownSpelling());
                        if (levelCode.equals(DictConstants.AreaLevel.COMMITTEE.getValue())) {
                            sysArea.setCommittee(sysArea.getName());
                            sysArea.setCommitteeShortName(sysArea.getShortName());
                            sysArea.setCommitteeSpelling(sysArea.getFullSpelling());
                        }
                    }
                }
            }
        }
        // 获取完整名称（简称拼接，简称为空则用全称）
        String proName = sysArea.getProvinceShortName();
        String cityName = StrUtil.isNotBlank(sysArea.getCityShortName()) ? sysArea.getCityShortName() : sysArea.getCity();
        String disName = StrUtil.isNotBlank(sysArea.getDistrictShortName()) ? sysArea.getDistrictShortName() : sysArea.getDistrict();
        String townName = StrUtil.isNotBlank(sysArea.getTownShortName()) ? sysArea.getTownShortName() : sysArea.getTown();
        String comName = StrUtil.isNotBlank(sysArea.getCommitteeShortName()) ? sysArea.getCommitteeShortName() : sysArea.getCommittee();
        String splitChar = SysConstants.Area.NAME_SPLIT_CHAR;
        String entireName = proName + (StrUtil.isNotBlank(cityName) ? (splitChar + cityName) : "") +
                (StrUtil.isNotBlank(disName) ? (splitChar + disName) : "") +
                (StrUtil.isNotBlank(townName) ? (splitChar + townName) : "") +
                (StrUtil.isNotBlank(comName) ? (splitChar + comName) : "");
        sysArea.setEntireName(entireName);
    }

    /**
     * 生成区划名称的拼音码
     *
     * @param sysArea 行政区划
     */
    private void createPinyin(SysArea sysArea) {
        String name = StrUtil.isNotBlank(sysArea.getShortName()) ? sysArea.getShortName() : sysArea.getName();
        // 获取区划拼音码简称
        sysArea.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(name));
        // 获取区划拼音码全拼
        sysArea.setFullSpelling(ChinesePinyinUtil.getPingYin(name));
        if (sysArea.getSimpleSpelling().length() > 0) {
            sysArea.setFirstChar(sysArea.getSimpleSpelling().substring(0, 1));
        }
    }

    /**
     * 根据区划id获取区划等级（兼容省市区划后面补0的情况）
     *
     * @param id 区划id
     * @return 区划等级
     */
    private String getAreaLevel(String id) {
        int length = id.length();
        // 如果区划长度不大于6 则去掉后面双0后在判断（兼容省市区划后面补0的情况）
        if (length <= SysConstants.Area.DISTRICT_CODE_LEN) {
            length = HStrUtil.removeSuffixDoubleZero(id).length();
        }
        return lenToLevel.get(length);
    }

    /**
     * 生成直接上级名称和完整上级名称
     *
     * @param sysArea    源行政区划
     * @param areaTreeVO 目标行政区划
     */
    private void createParentName(SysArea sysArea, AreaTreeVO areaTreeVO) {
        if (sysArea != null && areaTreeVO != null) {
            String levelCode = sysArea.getLevelType();
            String parentName = "";
            StringBuilder parentEntireName = new StringBuilder();
            String splitChar = SysConstants.Area.NAME_SPLIT_CHAR;
            if (levelCode.equals(PROVINCE.getValue())) {
                parentName = SysConstants.Area.CHINA;
                parentEntireName.append(parentName);
            } else if (levelCode.equals(CITY.getValue())) {
                parentName = sysArea.getProvince();
                parentEntireName.append(parentName);
            } else if (levelCode.equals(DISTRICT.getValue())) {
                parentName = sysArea.getCity();
                parentEntireName.append(sysArea.getProvince()).append(splitChar)
                        .append(sysArea.getCity());
            } else if (levelCode.equals(DictConstants.AreaLevel.TOWN.getValue())) {
                parentName = sysArea.getDistrict();
                parentEntireName.append(sysArea.getProvince()).append(splitChar)
                        .append(sysArea.getCity()).append(splitChar)
                        .append(sysArea.getDistrict());
            } else if (levelCode.equals(DictConstants.AreaLevel.COMMITTEE.getValue())) {
                parentName = sysArea.getTown();
                parentEntireName.append(sysArea.getProvince()).append(splitChar)
                        .append(sysArea.getCity()).append(splitChar)
                        .append(sysArea.getDistrict()).append(splitChar)
                        .append(sysArea.getTown());
            }
            areaTreeVO.setParentEntireName(parentEntireName.toString());
            areaTreeVO.setParentName(parentName);
        }
    }

    @Override
    public void createAreaJsonFile(String parentId) {
        List<HashMap<String, Object>> list = null;
        Map<String, String> resultMap;
        String id;
        String parentLevel = this.getAreaLevel(parentId);
        // 父区划编码为0 则生成所有区划数据
        if (parentId.equals(SysConstants.TOP_COMMON_CODE)) {
            // 获取所有省市区县数据
            list = sysAreaMapper.getLevel123Areas();
            resultMap = this.areaListTOMap(list);
            // 生成对应json文件
            this.createFile(parentId, resultMap);
        } else if (parentLevel.equals(PROVINCE.getValue()) || parentLevel.equals(CITY.getValue())) {
            // 省市获取区县区划
            list = sysAreaMapper.getDistricts(parentId);
        } else {
            // 生成子集区划文件
            this.createChildrenJsonFile(parentId);
        }
        if (list != null) {
            // 循环区县数据生成对应的街道居委json
            for (HashMap<String, Object> map : list) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if ("id".equals(entry.getKey())) {
                        id = (String) entry.getValue();
                        String level = this.getAreaLevel(id);
                        // 区县id
                        if (level.equals(DISTRICT.getValue())) {
                            this.createChildrenJsonFile(id);
                        }
                    }
                }
            }
        }
    }

    /**
     * 生成子集区划文件
     *
     * @param parentId 父id
     */
    private void createChildrenJsonFile(String parentId) {
        String level = this.getAreaLevel(parentId);
        if (level.equals(TOWN.getValue())) {
            // 居委list
            List<HashMap<String, Object>> list = sysAreaMapper.getAreaHashMap(parentId);
            // 转换为所需要的格式
            Map<String, String> resultMap = this.areaListTOMap(list);
            this.createFile(parentId, resultMap);
        } else if (level.equals(DISTRICT.getValue())) {
            // 区县list
            List<HashMap<String, Object>> list = sysAreaMapper.getAreaHashMap(parentId);
            // 转换为所需要的格式
            Map<String, String> resultMap = this.areaListTOMap(list);
            this.createFile(parentId, resultMap);
            // 循环区县数据生成对应的街道居委json
            for (HashMap<String, Object> map : list) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if ("id".equals(entry.getKey())) {
                        // 区县id
                        String id = (String) entry.getValue();
                        this.createChildrenJsonFile(id);
                    }
                }
            }
        }

    }

    /**
     * 地址HashMap-list转为区划map（id为key，name为value）
     *
     * @param list 地址
     * @return map
     */
    private Map<String, String> areaListTOMap(List<HashMap<String, Object>> list) {
        String name = "";
        String id = "";
        Map<String, String> resultMap = new HashMap<>(4000);
        for (HashMap<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if ("name".equals(entry.getKey())) {
                    name = (String) entry.getValue();
                } else if ("id".equals(entry.getKey())) {
                    id = (String) entry.getValue();
                }
            }
            resultMap.put(id, name);
        }
        return resultMap;
    }

    /**
     * 根据父级id和区划数据生成对应json文件
     *
     * @param parentId 父级id
     * @param areaMap  区划map
     */
    private void createFile(String parentId, Map<String, String> areaMap) {
        String parentLevel = this.getAreaLevel(parentId);
        String fileName = "";
        // 项目目录
        String projectPath = HFileUtil.getProjectPath();
        // 街道文件目录
        String townFileDirectory = SysConstants.Area.TOWN_FILE_DIRECTORY;
        // 居委文件目录
        String committeeFileDirectory = SysConstants.Area.COMMITTEE_FILE_DIRECTORY;
        // 文件扩展名
        String fileExtension = SysConstants.Area.FILE_EXTENSION;
        if (parentId.equals(SysConstants.TOP_COMMON_CODE)) {
            fileName = StrUtil.format(projectPath + "\\\\{}.{}",
                    SysConstants.Area.FILE_NAME, SysConstants.Area.FILE_EXTENSION);
        } else if (parentLevel.equals(DISTRICT.getValue())) {
            fileName = StrUtil.format(projectPath + "\\\\{}" + "\\\\{}.{}",
                    townFileDirectory,
                    parentId,
                    fileExtension);
        } else if (parentLevel.equals(TOWN.getValue())) {
            fileName = StrUtil.format(projectPath + "\\\\{}" + "\\\\{}.{}",
                    committeeFileDirectory,
                    parentId,
                    fileExtension);
        }
        if (areaMap.size() > 0 && StrUtil.isNotBlank(fileName)) {
            String areaJson = JSON.toJSONString(areaMap);
            log.info("生成区划文件的目录：" + fileName);
            FileUtil.writeUtf8String(areaJson, fileName);
        } else {
            log.info("没有需要生成的数据！");
        }
    }
}
