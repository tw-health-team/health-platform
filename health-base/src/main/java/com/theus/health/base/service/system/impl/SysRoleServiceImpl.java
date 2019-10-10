package com.theus.health.base.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.model.po.system.SysRole;
import com.theus.health.base.model.po.system.SysRoleResource;
import com.theus.health.base.model.po.system.SysUserRole;
import com.theus.health.base.mapper.system.SysRoleMapper;
import com.theus.health.base.model.dto.system.role.FindRoleDTO;
import com.theus.health.base.model.dto.system.role.RoleAddDTO;
import com.theus.health.base.model.dto.system.role.RoleUpdateDTO;
import com.theus.health.base.service.global.ShiroService;
import com.theus.health.base.service.system.SysRoleResourceService;
import com.theus.health.base.service.system.SysRoleService;
import com.theus.health.base.service.system.SysUserRoleService;
import com.theus.health.core.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangwei
 * @date 2019-07-23 23:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleResourceService roleResourceService;

    @Resource
    private SysUserRoleService userRoleService;

    @Resource
    private ShiroService shiroService;

    @Override
    public List<SysRole> findAllRoleByUserId(String uid, Boolean hasResource) {
        List<SysUserRole> userRoles = userRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", uid));
        List<SysRole> roles = new ArrayList<>();
        userRoles.forEach(v -> {
            SysRole role = this.getById(v.getRoleId());
            if (role != null) {
                if (hasResource) {
                    List<SysResource> permissions = roleResourceService.findAllResourceByRoleId(role.getId());
                    role.setResources(permissions);
                }
            }
            roles.add(role);
        });
        return roles;
    }

    @Override
    public IPage<SysRole> list(FindRoleDTO findRoleDTO) {
        // QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        // wrapper.orderBy(true, findRoleDTO.getAsc(), "id");
        // 获取角色list（分页）
        IPage<SysRole> rolePage = this.page(new Page<>(findRoleDTO.getPageNum(),
                findRoleDTO.getPageSize()));
        // 获取角色权限
        if (findRoleDTO.getHasResource()) {
            if (rolePage.getRecords() != null) {
                rolePage.getRecords().forEach(v ->
                        v.setResources(roleResourceService.findAllResourceByRoleId(v.getId())));
            }
        }
        return rolePage;
    }

    @Override
    public void remove(String rid) {
        SysRole role = this.getById(rid);
        if (role == null) {
            throw BusinessException.fail("角色不存在！");
        }
        try {
            this.removeById(rid);
            this.updateCache(role, true, false);
        } catch (DataIntegrityViolationException e) {
            throw BusinessException.fail(
                    String.format("请先解除角色为 %s 角色的全部用户！", role.getName()), e);
        } catch (Exception e) {
            throw BusinessException.fail("角色删除失败！", e);
        }
    }

    @Override
    public void update(String rid, RoleUpdateDTO roleUpdateDTO) {
        SysRole role = this.getById(rid);
        if (role == null) {
            throw BusinessException.fail("角色不存在！");
        }
        BeanUtils.copyProperties(roleUpdateDTO, role);
        try {
            this.updateById(role);
            roleResourceService.remove(new QueryWrapper<SysRoleResource>()
                    .eq("role_id", rid));
            if (roleUpdateDTO.getResources() != null) {
                for (SysResource sysResource : roleUpdateDTO.getResources()) {
                    roleResourceService.save(SysRoleResource.builder()
                            .resourceId(sysResource.getId())
                            .roleId(role.getId())
                            .build());
                }
            }
            this.updateCache(role, true, false);
        } catch (Exception e) {
            throw BusinessException.fail("角色更新失败！", e);
        }
    }

    @Override
    public void add(RoleAddDTO addDTO) {
        SysRole role = this.getOne(new QueryWrapper<SysRole>().eq("name", addDTO.getName()));
        if (role != null) {
            throw BusinessException.fail(
                    String.format("已经存在名称为 %s 的角色", addDTO.getName()));
        }
        role = new SysRole();
        BeanUtils.copyProperties(addDTO, role);
        this.save(role);
        if (addDTO.getResources() != null) {
            for (SysResource sysResource : addDTO.getResources()) {
                roleResourceService.save(SysRoleResource.builder()
                        .resourceId(sysResource.getId())
                        .roleId(role.getId())
                        .build());
            }
        }
    }

    @Override
    public void updateCache(SysRole role, Boolean author, Boolean out) {
        List<SysUserRole> sysUserRoles = userRoleService.list(new QueryWrapper<SysUserRole>()
                .select("user_id")
                .eq("role_id", role.getId())
                .groupBy("user_id"));
        List<String> userIdList = new ArrayList<>();
        if (sysUserRoles != null && sysUserRoles.size() > 0) {
            sysUserRoles.forEach(v -> userIdList.add(v.getUserId()));
        }
        shiroService.clearAuthByUserIdCollection(userIdList, author, out);
    }

    @Override
    public List<SysResource> findRoleResource(String roleId) {
        return roleResourceService.findAllResourceByRoleId(roleId);
    }

    @Override
    public void saveRoleResource(List<SysRoleResource> records) {
        if (records != null) {
            String roleId = records.get(0).getRoleId();
            roleResourceService.remove(new QueryWrapper<SysRoleResource>().eq("role_id", roleId));
            roleResourceService.saveBatch(records);
        }
    }
}
