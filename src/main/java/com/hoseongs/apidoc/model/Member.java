package com.hoseongs.apidoc.model;

public class Member {

	private String uid;
	private String pwd;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}

	public Member(String uid, String pwd) {
		super();
		this.uid = uid;
		this.pwd = pwd;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "Member [uid=" + uid + ", pwd=" + pwd + "]";
	}
	
}
