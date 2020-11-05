package com.waes.demo.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.waes.demo.domain.Request;
import com.waes.demo.domain.Side;
import com.waes.demo.repository.DemoRepository;

@Repository
public class DemoRepositoryImpl implements DemoRepository {

	private JdbcTemplate jdbcTemplate;

	DemoRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Integer save(Integer id, Request request, Side side) {
		String sql = "Update TBL_SIDES Set data = \'" + request.getReq() + "\' where sideId = " + id
				+ " and sideEnum = " + side.ordinal();

		return jdbcTemplate.update(sql);
	}

	@Override
	public String getById(Integer id, Side side) {
		String sql = "Select data from TBL_SIDES where sideId = " + id + " and sideEnum = " + side.ordinal();
		return jdbcTemplate.queryForObject(sql, String.class);
	}

}
