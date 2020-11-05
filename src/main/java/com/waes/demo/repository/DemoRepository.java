package com.waes.demo.repository;

import com.waes.demo.domain.Request;
import com.waes.demo.domain.Side;

public interface DemoRepository {

	public Integer save(Integer id, Request request, Side side);

	public String getById(Integer id, Side side);
}
