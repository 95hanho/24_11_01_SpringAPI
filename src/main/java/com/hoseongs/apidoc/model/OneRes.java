package com.hoseongs.apidoc.model;

public class OneRes extends Response {
	private Object data;
	
	public OneRes() {
	}
	public OneRes(int res_code, String res_message, Object data) {
		super(res_code, res_message);
		this.data = data;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "OneRes [res_code="+ super.getRes_code() +"res_message="+ super.getRes_message() +"result=" + this.data + "]";
	}
	
}
