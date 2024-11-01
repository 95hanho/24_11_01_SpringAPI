package com.hoseongs.apidoc.model;

public class Tokens extends Response {
	private String accessToken;
	private String refreshToken;
	
	public Tokens() {
		// TODO Auto-generated constructor stub
	}
	public Tokens(int res_code, String res_message, String accessToken, String refreshToken) {
		super(res_code, res_message);
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		
		return "Token [res_code=" + getRes_code() + ", res_message=" + getRes_message() + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken + "]";
	}
	
}
