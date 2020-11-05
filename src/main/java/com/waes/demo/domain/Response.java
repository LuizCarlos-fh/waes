package com.waes.demo.domain;

public class Response {

	private String result;

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Response(String result) {
		super();
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Response [result=" + result + ", getResult()=" + getResult() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
