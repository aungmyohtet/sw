package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.MailCountListener;
import com.frobom.sw.repository.MailCountListenerRepository;

@Repository
public class MailCountListenerRepositoryImpl implements MailCountListenerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(MailCountListener mailCountListener) {
        if (mailCountListener.getId() == null) {
            entityManager.persist(mailCountListener);
        } else {
            entityManager.merge(mailCountListener);
        }

    }

    @Override
    public List<MailCountListener> findAll() {
        return entityManager.createQuery("select m from MailCountListener m", MailCountListener.class).getResultList();
    }

    @Override
    public MailCountListener findById(int id) {
        return entityManager.find(MailCountListener.class, id);
    }

}