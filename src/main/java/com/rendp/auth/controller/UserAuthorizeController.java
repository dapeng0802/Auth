package com.rendp.auth.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rendp.auth.common.AjaxResult;
import com.rendp.auth.common.BaseEntity;
import com.rendp.auth.dto.Authorize;
import com.rendp.auth.entity.Role;
import com.rendp.auth.entity.User;
import com.rendp.auth.entity.UserRole;
import com.rendp.auth.service.RoleService;
import com.rendp.auth.service.UserService;

@Controller
@RequestMapping("/authorize")
public class UserAuthorizeController {

	@Autowired private UserService userService;
	@Autowired private RoleService roleService;
	
	/**
	 * 授权首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/security/authorize/authorize_list";
	}
	
	/**
	 * 用户角色首页
	 * @return
	 */
	@RequestMapping("/userRole")
	public String authorizeIndex() {
		return "/security/authorize/user_role_list";
	}
	
	/**
	 * 查询权限信息
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @return 权限集合
	 */
	@RequestMapping(value="/getAuthorizes", method=RequestMethod.POST)
	@ResponseBody
	public List<Authorize> getAuthorizes(Integer page, Integer size) {
		List<UserRole> userRoles = userService.getUserRoles(page, size);
		Collection<Long> userIds = new HashSet<>();
		Collection<Long> roleIds = new HashSet<>();
		userRoles.forEach((ur) -> {
			userIds.add(ur.getUserId());
			roleIds.add(ur.getRoleId());
		});
		
		List<User> users = userService.getUsers(userIds);
		List<Role> roles = roleService.getRoles(roleIds);
		
		Map<Long, User> userMap = BaseEntity.idEntityMap(users);
		Map<Long, Role> roleMap = BaseEntity.idEntityMap(roles);
		
		List<Authorize> authorizes = new LinkedList<Authorize>();
		userRoles.forEach(ur -> {
			Authorize authorize = new Authorize();
			authorize.setUserRoleId(ur.getId());
			authorize.setUserId(ur.getUserId());
			authorize.setUserName(userMap.get(ur.getUserId()).getName());
			authorize.setRoleId(ur.getRoleId());
			authorize.setRoleName(roleMap.get(ur.getRoleId()).getName());
			authorizes.add(authorize);
		});
		
		return authorizes;
	}
	
	/**
	 * 根据用户 ID 查询用户角色对应关系
	 * @param userId 用户 ID
	 * @return 用户角色对应关系集合
	 */
	@RequestMapping(value="/getUserRoleByUserId", method=RequestMethod.POST)
	@ResponseBody
	public List<UserRole> getUserRoleByUserId(Long userId) {
		return userService.getUserRolesByUserId(userId);
	}
	
	/**
	 * 设置权限
	 * @param user 用户
	 * @param roleIds 角色 ID 集合
	 * @return 操作结果
	 */
	@RequestMapping(value="/setAuthorize", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult setAuthorize(User user, String roleIds) {
		String[] temp = roleIds.split(",");
		Long[] roleIdArray = new Long[temp.length];
		for(int i = 0; i < roleIdArray.length; i++) {
			roleIdArray[i] = Long.valueOf(temp[i]);
		}
		userService.addUserRoles(user.getId(), roleIdArray);
		return AjaxResult.success();
	}
}
