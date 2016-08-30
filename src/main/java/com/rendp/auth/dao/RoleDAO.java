package com.rendp.auth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rendp.auth.common.BaseDAO;
import com.rendp.auth.entity.Role;

public class RoleDAO extends BaseDAO {
	
	private class RoleMapper implements RowMapper<Role> {

		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			role.setId(rs.getLong("id"));
			role.setName(rs.getString("name"));
			return role;
		}
	}
	
	/**
	 * 根据 ID 查询角色
	 * @param id 角色 ID
	 * @return 角色对象
	 */
	public Role findRoleById(Long id) {
		String sql = "select * from auth_role where id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RoleMapper());
	}
	
	/**
	 * 保存角色
	 * @param role 角色对象
	 */
	public void saveRole(Role role) {
		String sql = "insert into auth_role(name) values (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, role.getName());
				return ps;
			}
		}, keyHolder);
		
		role.setId(keyHolder.getKey().longValue());
	}
	
	/**
	 * 根据 ID 删除角色
	 * @param roleId 角色 ID
	 */
	public void deleteRole(Long roleId) {
		String sql = "delete from auth_role where id = ?";
		jdbcTemplate.update(sql, roleId);
	}
	
	/**
	 * 更新角色
	 * @param role 角色
	 */
	public void updateRole(Role role) {
		String sql = "update auth_role set name = ? where id = ?";
		jdbcTemplate.update(sql, role.getName(), role.getId());
	}
	
	/**
	 * 根据 ID 集合批量查询
	 * @param ids 角色 ID 集合
	 * @return 角色集合
	 */
	public List<Role> findRoles(Collection<Long> ids) {
		if(null == ids || ids.isEmpty()) {
			return new ArrayList<>();
		}
		StringBuilder sql = new StringBuilder("select * from auth_role where id in (");
		Object[] objs = new Object[ids.size()];
		AtomicInteger index = new AtomicInteger(0);
		ids.forEach((id) -> {
			sql.append("?,");
			objs[index.getAndIncrement()] = id;
		});
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return jdbcTemplate.query(sql.toString(), objs, new RoleMapper());
	}
	
	/**
	 * 分页查询角色信息
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @return 角色集合
	 */
	public List<Role> findRoles(int page, int size) {
		String sql = "select * from auth_role limit ?, ?";
		return jdbcTemplate.query(sql, new Object[]{(page - 1) * size, size}, new RoleMapper());
	}
}
