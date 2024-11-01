package com.hoseongs.apidoc.model;

public class Response {
	private int res_code;
	private String res_message;
	
	public Response() {
	}
	public Response(int res_code, String res_message) {
		super();
		this.res_code = res_code;
		this.res_message = res_message;
	}
	public int getRes_code() {
		return res_code;
	}
	public void setRes_code(int res_code) {
		this.res_code = res_code;
	}
	public String getRes_message() {
		return res_message;
	}
	public void setRes_message(String res_message) {
		this.res_message = res_message;
	}
	@Override
	public String toString() {
		return "Response [res_code=" + res_code + ", res_message=" + res_message + "]";
	}
	
}
