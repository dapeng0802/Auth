package com.rendp.auth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rendp.auth.common.BaseDAO;
import com.rendp.auth.entity.RoleFunction;

@Repository
public class RoleFunctionDAO extends BaseDAO {
	
	private class RoleFunctionMapper implements RowMapper<RoleFunction> {

		@Override
		public RoleFunction mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setId(rs.getLong("id"));
			roleFunction.setRoleId(rs.getLong("role_id"));
			roleFunction.setFunctionId(rs.getLong("function_id"));
			roleFunction.setStatus(rs.getInt("status"));
			return roleFunction;
		}
	}
	
	/**
	 * 根据 ID 查询角色功能对应关系
	 * @param id 角色功能对应关系 ID
	 * @return 角色功能对应关系
	 */
	public RoleFunction findRoleFunctionById(Long id) {
		String sql = "select * from auth_role_function where id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RoleFunctionMapper());
	}
	
	/**
	 * 保存角色功能对应关系集合
	 * @param roleFunction 角色功能对应关系集合
	 */
	public void saveRoleFunctions(Collection<RoleFunction> roleFunction) {
		String sql = "insert into auth_role_function(role_id, function_id, status) values (?, ?, ?)";
		List<Object[]> batchArgs = new ArrayList<>();
		roleFunction.forEach((rf) -> {
			Object[] objs = new Object[3];
			objs[0] = rf.getRoleId();
			objs[1] = rf.getFunctionId();
			objs[2] = rf.getStatus();
			
			batchArgs.add(objs);
		});
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	/**
	 * 根据角色 ID 查询角色功能对应关系集合
	 * @param roleId 角色 ID
	 * @return 角色功能对应关系集合
	 */
	public List<RoleFunction> findRoleFunctionsByRoleId(Long roleId) {
		String sql = "select * from auth_role_function where role_id = ?";
		return jdbcTemplate.query(sql, new Object[]{roleId}, new RoleFunctionMapper());
	}
	
	/**
	 * 根据角色 ID 删除角色功能对应关系集合 
	 * @param roleId 角色 ID
	 */
	public void deleteByRoleId(Long roleId) {
		String sql = "delete from auth_role_function where role_id = ?";
		jdbcTemplate.update(sql, roleId);
	}
}