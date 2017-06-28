package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.MailCount;
import com.frobom.sw.repository.MailCountRepository;

@Repository
public class MailCountRepositoryImpl implements MailCountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<MailCount> findAll() {
        return entityManager.createQuery("select m from MailCount m", MailCount.class).getResultList();
    }

}
