package com.waes.demo.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.waes.demo.domain.Request;
import com.waes.demo.domain.Side;
import com.waes.demo.repository.DemoRepository;

@SpringBootTest
public class DemoRepositoryImplTest {

	@Autowired
	private DemoRepository repository;

	@Test
	public void saveShouldReturnSuccess() {
		int result = repository.save(1, new Request(), Side.LEFT);

		assertThat(result).isEqualTo(1);
	}

	@Test
	public void saveShouldReturnFailure() {
		int result = repository.save(10, new Request(), Side.LEFT);

		assertThat(result).isEqualTo(0);
	}

	@Test
	public void getByIdShouldReturnSuccess() {
		String result = repository.getById(1, Side.LEFT);

		assertThat(result).isEqualTo("TGVmdCBTaWRlIFRlc3Q=");
	}

}
