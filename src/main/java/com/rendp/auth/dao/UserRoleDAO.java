package com.rendp.auth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.rendp.auth.common.BaseDAO;
import com.rendp.auth.entity.UserRole;

public class UserRoleDAO extends BaseDAO {
	
	private class UserRoleMapper implements RowMapper<UserRole> {

		@Override
		public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRole userRole = new UserRole();
			userRole.setId(rs.getLong("id"));
			userRole.setRoleId(rs.getLong("role_id"));
			userRole.setUserId(rs.getLong("user_id"));
			return userRole;
		}
	}
	
	/**
	 * 根据 ID 查询
	 * @param id ID
	 * @return 用户角色对应关系
	 */
	public UserRole fundUserRoleById(Long id) {
		String sql = "select * from auth_user_role where id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRoleMapper());
	}
	
	/**
	 * 保存用户角色对应关系
	 * @param userRole 用户角色对应关系
	 */
	public void saveUserRole(UserRole userRole) {
		String sql = "insert into auth_user_role(id, role_id, user_id) values (?, ?, ?)";
		jdbcTemplate.update(sql, userRole.getId(), userRole.getRoleId(), userRole.getUserId());
	}
	
	/**
	 * 根据用户 ID 删除用户角色对应关系
	 * @param userId 用户 ID
	 */
	public void deleteByUserId(Long userId) {
		String sql = "delete from auth_user_role where user_id = ?";
		jdbcTemplate.update(sql, userId);
	}
	
	/**
	 * 分页查询用户角色对应关系
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @return 用户角色对应关系集合
	 */
	public List<UserRole> findUserRoles(int page, int size) {
		String sql = "select * from auth_role_user order by user_id limit ?, ?";
		return jdbcTemplate.query(sql, new Object[]{(page - 1) * size, size}, new UserRoleMapper());
	}
	
	/**
	 * 根据用户 ID 查询用户角色对应关系
	 * @param userId 用户 ID
	 * @return
	 */
	public List<UserRole> findUserRoleByUserId(Long userId) {
		String sql = "select * from auth_user_role where user_id = ?";
		return jdbcTemplate.query(sql, new Object[]{userId}, new UserRoleMapper());
	}
	
	/**
	 * 保存用户角色对应关系集合
	 * @param userRoles 用户角色对应关系集合
	 */
	public void saveUserRoles(Collection<UserRole> userRoles) {
		String sql = "insert into auth_user_role(user_id, role_id) values (?, ?)";
		List<Object[]> batchArgs = new ArrayList<>();
		userRoles.forEach((ur) -> {
			Object[] objs = new Object[2];
			objs[0] = ur.getUserId();
			objs[1] = ur.getRoleId();
			
			batchArgs.add(objs);
		});
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
}
