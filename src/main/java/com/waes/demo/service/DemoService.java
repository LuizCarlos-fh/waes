package com.waes.demo.service;

import com.waes.demo.domain.Request;
import com.waes.demo.domain.Side;

public interface DemoService {

	public String updateData(Integer id, Request request, Side side);

	public String checkDataDiff(Integer id);
}
