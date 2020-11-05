package com.waes.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waes.demo.domain.Request;
import com.waes.demo.domain.Response;
import com.waes.demo.domain.Side;
import com.waes.demo.service.DemoService;

@RestController
@RequestMapping("/v1/diff")
public class DemoController {

	private static final Logger LOG = LogManager.getLogger(DemoController.class);
	private static final String DATA_ERROR_MESSAGE = "An error was encountered. Database error ID provided was not found";
	private static final String ERROR_MESSAGE = "An error was encountered. Please contact your system administrator";
	
	private final DemoService service;

	DemoController(DemoService service) {
		this.service = service;
	}

	@PutMapping(value = "/{id}/left")
	public Response diffLeft(@PathVariable Integer id, @RequestBody Request request) {

		Response response = new Response();

		try {
			response.setResult(service.updateData(id, request, Side.LEFT));
		} catch (DataAccessException ex) {
			response.setResult(DATA_ERROR_MESSAGE);
			LOG.error(ex.getMessage());
		} catch (Exception ex) {
			response.setResult(ERROR_MESSAGE);
			LOG.error(ex.getMessage());
		}

		return response;
	}

	@PutMapping("/{id}/right")
	public Response diffRight(@PathVariable Integer id, @RequestBody Request request) {

		Response response = new Response();

		try {
			response.setResult(service.updateData(id, request, Side.RIGHT));
		} catch (DataAccessException ex) {
			response.setResult(DATA_ERROR_MESSAGE);
			LOG.error(ex.getMessage());
		} catch (Exception ex) {
			response.setResult(ERROR_MESSAGE);
			LOG.error(ex.getMessage());
		}

		return response;
	}

	@GetMapping(value = "/{id}")
	public Response diff(@PathVariable Integer id) {

		Response response = new Response();

		try {
			response.setResult(service.checkDataDiff(id));
		} catch (DataAccessException ex) {
			response.setResult(DATA_ERROR_MESSAGE);
			LOG.error(ex.getMessage());
		} catch (Exception ex) {
			response.setResult(ERROR_MESSAGE);
			LOG.error(ex.getMessage());
		}

		return response;
	}
}
