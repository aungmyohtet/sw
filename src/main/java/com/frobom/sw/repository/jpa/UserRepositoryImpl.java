package com.frobom.sw.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.User;
import com.frobom.sw.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private PasswordEncoder passwordEnconder;

	public void setPasswordEnconder(PasswordEncoder passwordEnconder) {
		this.passwordEnconder = passwordEnconder;
	}

	@Override
	public void add(User user) {
		user.setPassword(passwordEnconder.encode(user.getPassword()));
		entityManager.persist(user);
	}

	@Override
	public User findByEmail(String email) {
		User user = null;
		try {
			Query q = entityManager.createQuery("SELECT u FROM User u WHERE u.email=?");
			q.setParameter(1, email);
			user = (User) q.getSingleResult();
		} catch (NoResultException e) {

		}

		return user;
	}

}
