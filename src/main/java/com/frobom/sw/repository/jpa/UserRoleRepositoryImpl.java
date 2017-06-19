package com.frobom.sw.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.UserRole;
import com.frobom.sw.repository.UserRoleRepository;

@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(UserRole userRole) {
        entityManager.persist(userRole);
	}

}
