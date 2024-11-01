package com.hoseongs.apidoc.model;

import java.util.List;

public class Test {
	private String id;
	private String nick;
	private List<String> list;
	
	Test() {}
	
	public Test(String id, String nick) {
		super();
		this.id = id;
		this.nick = nick;
	}
	
	public Test(String id, String nick, List<String> list) {
		super();
		this.id = id;
		this.nick = nick;
		this.list = list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", nick=" + nick + ", list=" + list + "]";
	}
}
