package com.hoseongs.apidoc.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoseongs.apidoc.model.User;
import com.hoseongs.apidoc.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository uDAO;
	
	@Override
	public ArrayList<User> getUsers() {
		return uDAO.getUsers();
	}
	
	@Override
	public User getUser(String id) {
		return uDAO.getUser(id);
	}

	@Override
	public String getPwd(String id) {
		return uDAO.getPwd(id);
	}

	@Override
	public User login(String id, String refreshToken) {
		return uDAO.login(id, refreshToken);
	}

	@Override
	public User getUserFromToken(String refreshToken) {
		return uDAO.getUserFromToken(refreshToken);
	}

	@Override
	public int joinUser(User user) {
		return uDAO.joinUser(user);
	}

}
