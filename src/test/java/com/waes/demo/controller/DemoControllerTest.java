package com.waes.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.waes.demo.domain.Response;
import com.waes.demo.domain.Side;
import com.waes.demo.service.DemoService;

@SpringBootTest
public class DemoControllerTest {

	private static final String ERROR_MESSAGE = "An error was encountered. Please contact your system administrator";
	private static final String SUCCESS_MESSAGE = "success";

	@Autowired
	private DemoController controller;

	@MockBean
	private DemoService service;

	@Test
	public void diffLeftShouldReturnSuccess() {
		when(service.updateData(1, null, Side.LEFT)).thenReturn(SUCCESS_MESSAGE);

		Response response = controller.diffLeft(1, null);

		assertThat(response.getResult().contains(SUCCESS_MESSAGE));
	}

	@Test
	public void diffLeftShouldReturnException() {
		when(service.updateData(1, null, Side.LEFT)).thenThrow(NullPointerException.class);

		Response response = controller.diffLeft(1, null);

		assertThat(response.getResult().contains(ERROR_MESSAGE));
	}

	@Test
	public void diffRightShouldReturnSuccess() {
		when(service.updateData(1, null, Side.RIGHT)).thenReturn(SUCCESS_MESSAGE);

		Response response = controller.diffRight(1, null);
		assertThat(response.getResult().contains(SUCCESS_MESSAGE));
	}

	@Test
	public void diffRightShouldReturnException() {
		when(service.updateData(1, null, Side.RIGHT)).thenThrow(NullPointerException.class);

		Response response = controller.diffRight(1, null);

		assertThat(response.getResult().contains(ERROR_MESSAGE));
	}

	@Test
	public void diffShouldReturnSuccess() {
		when(service.checkDataDiff(1)).thenReturn(SUCCESS_MESSAGE);

		Response response = controller.diff(1);

		assertThat(response.getResult().contains(SUCCESS_MESSAGE));
	}

	@Test
	public void diffShouldReturnException() {
		when(service.checkDataDiff(1)).thenThrow(NullPointerException.class);

		Response response = controller.diff(1);

		assertThat(response.getResult().contains(ERROR_MESSAGE));
	}
}
