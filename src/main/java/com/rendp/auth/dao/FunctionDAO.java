package com.rendp.auth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rendp.auth.common.BaseDAO;
import com.rendp.auth.entity.Function;

public class FunctionDAO extends BaseDAO {
	
	private class FunctionMapper implements RowMapper<Function> {

		@Override
		public Function mapRow(ResultSet rs, int rowNum) throws SQLException {
			Function function = new Function();
			function.setId(rs.getLong("id"));
			function.setName(rs.getString("name"));
			function.setParentId(rs.getLong("parent_id"));
			function.setSerialNum(rs.getInt("serial_num"));
			function.setUrl(rs.getString("url"));
			function.setAccordion(rs.getInt("accordion"));
			return function;
		}
	}
	
//	public Function findFunctionById(Long id) {
//		String sql = "select * from auth_function where id = ?";
//		return jdbcTemplate.queryForObject(sql, new Object[]{id}, new FunctionMapper());
//	}
	
	/**
	 * 保存功能
	 * @param function 功能对象
	 */
	public void saveFunction(Function function) {
		String sql = "insert into auth_function(id, name, parentId, serialNum, url, accordion) values (?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, function.getName());
				ps.setLong(2, function.getParentId());
				ps.setString(3, function.getUrl());
				ps.setInt(4, function.getSerialNum());
				ps.setInt(5, function.getAccordion());
				return ps;
			}
		}, keyHolder);
		
		function.setId(keyHolder.getKey().longValue());
	}
	
	/**
	 * 根据 ID 更新功能的 URL
	 * @param id 功能 ID
	 * @param url URL
	 */
	public void updateUrl(Long id, String url) {
		String sql = "update auth_function set url = ? where id = ?";
		jdbcTemplate.update(sql, url, id);
	}
	
	/**
	 * 根据 ID 删除功能
	 * @param id 功能 ID
	 */
	public void deleteById(Long id) {
		String sql = "delete from auth_function where id = ?";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 分页查询功能（根据父节点）
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @param parentId 父节点 ID
	 * @return 功能集合
	 */
	public List<Function> findFunctions(int page, int size, Long parentId) {
		String sql = "select * from auth_function where parent_id = ? limit ?, ?";
		return jdbcTemplate.query(sql, new Object[]{parentId, (page - 1) * size, size}, new FunctionMapper());
	}
	
	/**
	 * 查询全部功能
	 * @return 功能集合
	 */
	public List<Function> getAllFunctions() {
		return jdbcTemplate.query("select * from auth_function", new FunctionMapper());
	}
}
