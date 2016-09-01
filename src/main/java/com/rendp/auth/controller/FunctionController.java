package com.rendp.auth.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rendp.auth.common.AjaxResult;
import com.rendp.auth.common.tree.Node;
import com.rendp.auth.common.tree.Tree;
import com.rendp.auth.context.NativeCache;
import com.rendp.auth.entity.Function;
import com.rendp.auth.service.FunctionService;

@Controller
@RequestMapping("/function")
public class FunctionController {
	
	@Autowired private FunctionService functionService;
	@Autowired private NativeCache nativeCache;
	
	/**
	 * 菜单首页
	 * @return
	 */
	@RequestMapping(value="/index")
	public String menuList() {
		return "/security/function/function_list";
	}
	
	/**
	 * 新建、修改功能
	 * @param function 功能
	 * @return 操作结果
	 */
	@RequestMapping(value="/addEditFunction", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult addEditFunction(Function function) {
		if(null == function.getId()) {
			function.setSerialNum(nativeCache.getFunctions().size());
			functionService.addFunction(function);
			nativeCache.addFunction(function);
		} else {
			functionService.updateUrl(function.getId(), function.getUrl());
			nativeCache.replaceFunction(function);
		}
		return AjaxResult.success();
	}
	
	/**
	 * 根据 ID 删除功能信息
	 * @param id 功能 ID
	 * @return 操作结果
	 */
	@RequestMapping(value="/deleteFunction", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteFunctionById(Long id) {
		functionService.deleteFunctionById(id);
		nativeCache.removeFunction(id);
		return AjaxResult.success();
	}
	
	/**
	 * 查询子功能信息
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @param parentId 父节点 ID
	 * @return 子功能集合
	 */
	@RequestMapping(value="/getSubFunctions", method=RequestMethod.POST)
	@ResponseBody
	public List<Function> getSubFunctions(Integer page, Integer size, Long parentId) {
		if(Objects.equals(null, parentId)) {
			parentId = 0L;
		}
		return functionService.getFunctions(page, size, parentId);
	}
	
	/**
	 * 构建用于新建、修改使用的菜单（功能）树
	 * @return 包含父子关系的树型节点集合
	 */
	@RequestMapping(value="/buildMenuTreeForEdit", method=RequestMethod.POST)
	@ResponseBody
	public List<Node> buildMenuTreeForEdit() {
		List<Function> functionList = nativeCache.getFunctions();
		return (new Tree(functionList)).build();
	}
}
