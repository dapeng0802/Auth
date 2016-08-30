package com.rendp.auth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.jdbc.core.RowMapper;

import com.rendp.auth.common.BaseDAO;
import com.rendp.auth.entity.User;

public class UserDAO extends BaseDAO {
	
	private class UserMapper implements RowMapper<User> {
		
		public User mapRow(ResultSet rs, int index) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setPwd(rs.getString("pwd"));
			return user;
		}
	}
	
	/**
	 * 根据用户名、密码查询用户，用于登录
	 * @param name  用户名
	 * @param pwd  密码
	 * @return  查询到的唯一用户实体
	 */
	public User findUser(String name, String pwd) {
		String sql = "select * from auth_user where name = ? and pwd = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[]{name, pwd}, new UserMapper());
		} catch(Exception e) {
			return null;
		}
	}
	
	public void saveUser(User user) {
		String sql = "insert into auth_user(id, name, pwd) values (?, ?, ?)";
		jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPwd());
	}
	
	public void deleteById(Long id) {
		String sql = "delete from auth_user where id = ?";
		jdbcTemplate.update(sql);
	}
	
	public User findById(Long id) {
		String sql = "select * from auth_user where id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserMapper());
	}
	
	public Collection<User> findByIds(Collection<Long> ids) {
		StringBuilder sql = new StringBuilder("select * from auth_user where id in (");
		ids.forEach((id) -> sql.append(id).append("?, "));
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(")");
		return jdbcTemplate.query(sql.toString(), ids.toArray(new Object[0]), new UserMapper());
	}
	
	/**
	 * 分页查询用户信息
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @return 用户集合
	 */
	public Collection<User> findPage(int page, int size) {
		if(page < 1) {
			page = 1;
		}
		if(size < 0) {
			size = 20;
		}
		String sql = "select * from auth_user limit ?, ?";
		int skip = (page - 1) * size;
		return jdbcTemplate.query(sql, new Object[]{skip, size}, new UserMapper());
	}
}
