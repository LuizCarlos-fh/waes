package com.waes.demo.domain;

public class Request {

	private String req;

	public Request() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Request(String req) {
		super();
		this.req = req;
	}

	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
	}

	@Override
	public String toString() {
		return "Request [req=" + req + "]";
	}
}
