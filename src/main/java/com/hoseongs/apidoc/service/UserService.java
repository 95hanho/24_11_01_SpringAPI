package com.hoseongs.apidoc.service;

import java.util.ArrayList;

import com.hoseongs.apidoc.model.User;

public interface UserService {

	ArrayList<User> getUsers();
	
	User getUser(String id);

	String getPwd(String id);

	User login(String id, String refreshToken);

	User getUserFromToken(String refreshToken);

	int joinUser(User user);

}
