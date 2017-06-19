package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.User;
import com.frobom.sw.repository.UserRepository;
import com.frobom.sw.repository.UserRoleRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	private UserRoleRepository userRoleRepository;

	public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	@Transactional
	public void add(User user) {
		userRepository.add(user);
	}

	@Override
	@Transactional
	public User findById(long id) {
		return null;
	}

	@Override
	@Transactional
	public List<User> findAll() {
		return null;
	}
	
	@Override
	@Transactional
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
