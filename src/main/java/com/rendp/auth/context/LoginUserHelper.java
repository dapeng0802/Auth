package com.rendp.auth.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.rendp.auth.common.Whether;
import com.rendp.auth.dto.Accordion;
import com.rendp.auth.entity.Function;
import com.rendp.auth.entity.Role;
import com.rendp.auth.entity.RoleFunction;
import com.rendp.auth.entity.User;
import com.rendp.auth.entity.UserRole;
import com.rendp.auth.service.RoleService;
import com.rendp.auth.service.UserService;

public class LoginUserHelper {

	@Autowired private UserService userService;
	
	@Autowired private RoleService roleService;
	
	@Autowired private NativeCache nativeCache;
	
	public void executeLogin(String username, String pwd) {
		User user = userService.getUser(username, pwd);
		List<UserRole> userRoles = userService.getUserRolesByUserId(user.getId());
		if(null == userRoles || 0 == userRoles.size()) {
			return;
		}
		List<Long> roleIds = new ArrayList<Long>();
		for(UserRole ur : userRoles) {
			roleIds.add(ur.getRoleId());
		}
		List<Role> roles = roleService.getRoles(roleIds);
		nativeCache.setRoles(user.getId(), roles);
		
		LoginUserCache.put(user);
		List<Accordion> accordions = getAccordions(false, user.getId());
		LoginUserCache.setAccordions(user.getName(), accordions);
	}

	private List<Accordion> getAccordions(boolean isAdmin, Long userId) {
		Set<String> permissionUrls = new HashSet<>();
		Set<Long> rootFunctionIdSet = new HashSet<>();
		if(!isAdmin) {
			for(Role role : nativeCache.getRoles(UserContext.getCurrent().getUser().getId())) {
				List<RoleFunction> roleFunctions = roleService.getRoleFunctions(role.getId());
				for(RoleFunction rf : roleFunctions) {
					Function func = nativeCache.getFunction(rf.getFunctionId());
					if(Objects.equals(func.getAccordion(), Whether.YES.getValue())) {
						rootFunctionIdSet.add(func.getId());
					} else {
						permissionUrls.add(func.getUrl());
					}
				}
			}
		}
		
		Map<Long, Accordion> accordionMap = new HashMap<>();
		List<Accordion> permissionAccordionSet = new LinkedList<>();
		
		List<Function> functions = nativeCache.getFunctions();
		
		List<Accordion> rootAccordionSet = new LinkedList<>();
		for(Function function : functions) {
			Accordion accordion = new Accordion(function.getId(), function.getParentId(), function.getName(), function.getUrl(), function.getSerialNum());
			accordionMap.put(function.getId(), accordion);
			if(!isAdmin) {
				if(permissionUrls.contains(function.getUrl())) {
					permissionAccordionSet.add(accordion);
				}
				if(rootFunctionIdSet.contains(function.getId())) {
					rootAccordionSet.add(accordion);
				}
			} else {
				permissionAccordionSet.add(accordion);
				if(Whether.YES.getValue() == function.getAccordion()) {
					rootAccordionSet.add(accordion);
				}
			}
		}
		Collections.sort(rootAccordionSet);
		Collections.sort(permissionAccordionSet);
		for(Accordion accordion : rootAccordionSet) {
			completeAccordion(permissionAccordionSet, accordion);
		}
		return rootAccordionSet;
	}
	
	private void completeAccordion(List<Accordion> permissionAccordionSet,
			Accordion rootAccordion) {
		for(Accordion accordion : permissionAccordionSet) {
			if(Objects.equals(accordion.getParentId(), rootAccordion.getId())) {
				rootAccordion.getChildren().add(accordion);
			}
		}
	}
}
