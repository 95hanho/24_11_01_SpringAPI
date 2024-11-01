package com.hoseongs.apidoc.repository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoseongs.apidoc.mapper.UserMapper;
import com.hoseongs.apidoc.model.User;

@Repository
public class UserRepository {

	@Autowired
	private UserMapper uMapper; 
	
	public ArrayList<User> getUsers() {
		return uMapper.getUsers();
	}
	
	public User getUser(String id) {
		return uMapper.getUser(id);
	}

	public String getPwd(String id) {
		return uMapper.getPwd(id);
	}

	public User login(String id, String refreshToken) {
		uMapper.setToken(id, refreshToken);
		return uMapper.getUser(id);
	}

	public User getUserFromToken(String refreshToken) {
		System.out.println(refreshToken);
		return uMapper.getUserFromToken(refreshToken);
	}

	public int joinUser(User user) {
		return uMapper.joinUser(user);
	}

	
}
