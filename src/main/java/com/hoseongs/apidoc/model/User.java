package com.hoseongs.apidoc.model;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

public class User {
	private String id;
	private String nickName;
	private String password;
	private Date createDate;
	private String refreshToken;
	
	public User() {};
	
	public User(String id, String nickName, Date createDate) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.createDate = createDate;
	}
	
	public User(String id, String nickName, String password, Date createDate) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.password = password;
		this.createDate = createDate;
	}

	public User(String id, String nickName, String password, Date createDate, String refreshToken) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.password = password;
		this.createDate = createDate;
		this.refreshToken = refreshToken;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nickName=" + nickName + ", password=" + password + ", createDate=" + createDate
				+ ", refreshToken=" + refreshToken + "]";
	}
	
}
