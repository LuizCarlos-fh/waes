package com.waes.demo.service.impl;

import java.util.Arrays;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.waes.demo.domain.Request;
import com.waes.demo.domain.Side;
import com.waes.demo.repository.DemoRepository;
import com.waes.demo.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService {

	private static final String ERROR_MESSAGE = "Invalid request";
	private static final String DATA_UPDATE_MESSAGE = "Data Updated Sucessfully";
	private static final String SIDE_NOT_SAME_SIZE_MESSAGE = "It is not the same size!";
	private static final String SIDE_EQUAL_MESSAGE = "It is equal!";

	private final DemoRepository repository;

	DemoServiceImpl(DemoRepository repository) {
		this.repository = repository;
	}

	@Override
	public String updateData(Integer id, Request request, Side side) {

		// check if parameters are valid
		if (!paramValidator(id, request, side)) {
			return ERROR_MESSAGE;
		}

		repository.save(id, request, side);

		return DATA_UPDATE_MESSAGE;
	}

	private boolean paramValidator(Integer id, Request request, Side side) {
		return request.getReq() != null && Base64.isBase64(request.getReq().toString().getBytes()) && id != null
				&& repository.getById(id, side) != null;
	}

	public String checkDataDiff(Integer id) {

		if (id == null) {
			return ERROR_MESSAGE;
		}

		String leftSide = repository.getById(id, Side.LEFT);
		if (leftSide == null) {
			return ERROR_MESSAGE;
		}

		String rightSide = repository.getById(id, Side.RIGHT);
		if (rightSide == null) {
			return ERROR_MESSAGE;
		}

		byte[] leftSideDecoded = stringDecoder(leftSide);
		byte[] rightSideDecoded = stringDecoder(rightSide);

		// check if equal
		if (isSidesEqual(leftSideDecoded, rightSideDecoded)) {
			return SIDE_EQUAL_MESSAGE;
		}

		// check size
		if (!isSidesSizeSame(leftSideDecoded, rightSideDecoded)) {
			return SIDE_NOT_SAME_SIZE_MESSAGE;
		}

		// check diffs
		HashMap<String, String> diff = checkSidesDiff(leftSideDecoded, rightSideDecoded);

		return diff.toString();
	}

	private boolean isSidesEqual(byte[] left, byte[] right) {
		return Arrays.equals(left, right);
	}

	private boolean isSidesSizeSame(byte[] left, byte[] right) {
		return left.length == right.length;
	}

	private byte[] stringDecoder(String base64Encoded) {
		return DatatypeConverter.parseBase64Binary(base64Encoded);
	}

	private HashMap<String, String> checkSidesDiff(byte[] left, byte[] right) {

		int offset = 0;

		HashMap<String, String> offsetsMap = new HashMap<String, String>();

		for (int i = 1; i < left.length - 1; i++) {

			if (left[i] != right[i] && offset == 0) {
				offset = i;
			} else {
				if (left[i] == right[i] && offset != 0) {
					offsetsMap.put("offset:" + String.valueOf(offset), "length:" + String.valueOf(i - offset));
					offset = 0;
				}
			}
		}

		if (offset != 0) {
			offsetsMap.put("offset:" + String.valueOf(offset), "length:" + String.valueOf(left.length - offset));
		}

		return offsetsMap;
	}
}
