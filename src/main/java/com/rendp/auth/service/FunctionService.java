package com.rendp.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rendp.auth.dao.FunctionDAO;
import com.rendp.auth.entity.Function;

@Service
public class FunctionService {
	
	@Autowired private FunctionDAO functionDAO;
	
	/**
	 * 增加功能
	 * @param function 功能
	 */
	public void addFunction(Function function) {
		functionDAO.saveFunction(function);
	}
	
	/**
	 * 根据功能 ID 更新其 URL 信息
	 * @param id 功能 ID
	 * @param url URL
	 */
	public void updateUrl(Long id, String url) {
		functionDAO.updateUrl(id, url);
	}
	
	/**
	 * 分页查询指定父节点的子节点
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @param parentId 父节点 ID
	 * @return 功能集合
	 */
	public List<Function> getFunctions(int page, int size, Long parentId) {
		return functionDAO.findFunctions(page, size, parentId);
	}
	
	/**
	 * 根据 ID 删除功能
	 * @param id 功能 ID
	 */
	public void deleteFunctionById(Long id) {
		functionDAO.deleteById(id);
	}
	
	/**
	 * 查询全部功能信息
	 * @return 功能集合
	 */
	public List<Function> getAllFunctions() {
		return functionDAO.getAllFunctions();
	}
}