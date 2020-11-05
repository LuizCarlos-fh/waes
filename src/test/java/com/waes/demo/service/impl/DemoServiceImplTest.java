package com.waes.demo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.waes.demo.domain.Request;
import com.waes.demo.domain.Side;
import com.waes.demo.repository.DemoRepository;
import com.waes.demo.service.DemoService;

@SpringBootTest
public class DemoServiceImplTest {

	private static final String LEFT_BASE64 = "TGVmdCBTaWRlIFRlc3Q=";
	private static final String RIGHT_BASE64 = "UmlnaHRTaWRlIG9rYXk=";
	private static final String OTHER_BASE64 = "T3RoZXIgVGVzdA==";
	private static final String ERROR_MESSAGE = "Invalid request";
	private static final String SUCCESS_MESSAGE = "Data Updated Sucessfully";

	@Autowired
	private DemoService service;

	@MockBean
	private DemoRepository repository;

	@Test
	public void updateDataShouldReturnInvalidRequestByInvalidRequest() {
		String result = service.updateData(1, new Request(null), Side.LEFT);
		assertThat(result.contains(ERROR_MESSAGE));
	}

	@Test
	public void updateDataShouldReturnInvalidRequestByInvalidBase64Request() {
		String result = service.updateData(1, new Request(ERROR_MESSAGE), Side.LEFT);
		assertThat(result.contains(ERROR_MESSAGE));
	}

	@Test
	public void updateDataShouldReturnInvalidRequestByInvalidId() {
		String result = service.updateData(null, new Request(LEFT_BASE64), Side.LEFT);
		assertThat(result.contains(ERROR_MESSAGE));
	}

	@Test
	public void updateDataShouldReturnInvalidRequestByIdNotFound() {
		when(repository.getById(1, Side.LEFT)).thenReturn(null);

		String result = service.updateData(1, new Request(LEFT_BASE64), Side.LEFT);
		assertThat(result.contains(ERROR_MESSAGE));
	}

	@Test
	public void updateDataShouldReturnSuccess() {
		Request request = new Request(LEFT_BASE64);
		when(repository.save(1, request, Side.LEFT)).thenReturn(1);
		when(repository.getById(1, Side.LEFT)).thenReturn("ID");

		String result = service.updateData(1, request, Side.LEFT);
		assertThat(result.contains(SUCCESS_MESSAGE));
	}

	@Test
	public void checkDataDiffShouldReturnInvalidRequestByInvalidId() {
		String result = service.checkDataDiff(null);
		assertThat(result.contains(ERROR_MESSAGE));
	}

	@Test
	public void checkDataDiffShouldReturnInvalidRequestByLeftSideIdNotFound() {
		when(repository.getById(1, Side.LEFT)).thenReturn(null);
		String result = service.checkDataDiff(1);
		assertThat(result.contains(ERROR_MESSAGE));
	}

	@Test
	public void checkDataDiffShouldReturnInvalidRequestByRightSideIdNotFound() {
		when(repository.getById(1, Side.LEFT)).thenReturn(LEFT_BASE64);
		when(repository.getById(1, Side.RIGHT)).thenReturn(null);
		String result = service.checkDataDiff(1);
		assertThat(result.contains(ERROR_MESSAGE));
	}

	@Test
	public void checkDataDiffShouldReturnSuccessByEqual() {
		when(repository.getById(1, Side.LEFT)).thenReturn(OTHER_BASE64);
		when(repository.getById(1, Side.RIGHT)).thenReturn(OTHER_BASE64);
		String result = service.checkDataDiff(1);
		assertThat(result.contains("It is equal!"));
	}

	@Test
	public void checkDataDiffShouldReturnSuccessByNotSameSize() {
		when(repository.getById(1, Side.LEFT)).thenReturn(LEFT_BASE64);
		when(repository.getById(1, Side.RIGHT)).thenReturn(OTHER_BASE64);
		String result = service.checkDataDiff(1);
		assertThat(result.contains("It is not the same size!"));
	}

	@Test
	public void checkDataDiffShouldReturnSuccessByListDiff() {
		when(repository.getById(1, Side.LEFT)).thenReturn(LEFT_BASE64);
		when(repository.getById(1, Side.RIGHT)).thenReturn(RIGHT_BASE64);
		String result = service.checkDataDiff(1);
		assertThat(result.contains("{offset:1=length:4}"));
	}

}
