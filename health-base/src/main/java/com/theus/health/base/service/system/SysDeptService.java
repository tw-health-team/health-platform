package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.dto.SignInDTO;
import com.theus.health.base.model.dto.system.dept.DeptAddDTO;
import com.theus.health.base.model.dto.system.dept.DeptUpdateDTO;
import com.theus.health.base.model.dto.system.dept.FindDeptDTO;
import com.theus.health.base.model.dto.system.user.FindUserDTO;
import com.theus.health.base.model.dto.system.user.ResetPasswordDTO;
import com.theus.health.base.model.dto.system.user.UserAddDTO;
import com.theus.health.base.model.dto.system.user.UserUpdateDTO;
import com.theus.health.base.model.po.system.SysDept;
import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.model.po.system.SysRole;
import com.theus.health.base.model.po.system.SysUser;
import com.theus.health.base.model.vo.SysUserVO;

import java.util.List;
import java.util.Set;

/**
 * @author tangwei
 * @date 2019-09-25 20:19
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 根据机构名称查找机构
     * @param name 机构名
     * @return 机构信息
     */
    SysDept findDeptByName(String name);

    /**
     * 添加机构
     * @param addDTO 机构数据DTO
     */
    void add(DeptAddDTO addDTO);

    /**
     * 更新机构数据
     * @param id 机构id
     * @param updateDTO 机构数据DTO
     */
    void update(String id, DeptUpdateDTO updateDTO);

    /**
     * 查询机构树
     * @param findDeptDTO 机构查询条件
     * @return 机构树
     */
    List<SysDept> findTree(FindDeptDTO findDeptDTO);

}
