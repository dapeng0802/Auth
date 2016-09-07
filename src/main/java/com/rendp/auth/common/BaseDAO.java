package com.rendp.auth.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class BaseDAO {
	
	@Autowired protected JdbcTemplate jdbcTemplate;
}