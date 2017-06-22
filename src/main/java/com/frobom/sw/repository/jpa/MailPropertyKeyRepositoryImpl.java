package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.MailPropertyKey;
import com.frobom.sw.repository.MailPropertyKeyRepository;

@Repository
public class MailPropertyKeyRepositoryImpl implements MailPropertyKeyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(MailPropertyKey mailPropertyKey) {
        if (mailPropertyKey.getId() == null) {
            entityManager.persist(mailPropertyKey);
        } else {
            entityManager.merge(mailPropertyKey);
        }
    }

    @Override
    public List<MailPropertyKey> findAll() {
        return entityManager.createQuery("select m from MailPropertyKey m", MailPropertyKey.class).getResultList();
    }

    @Override
    public MailPropertyKey findById(int id) {
        return entityManager.find(MailPropertyKey.class, id);
    }

    @Override
    public MailPropertyKey findByName(String name) {
        Query query = entityManager.createQuery("from MailPropertyKey k where k.name=:name", MailPropertyKey.class);
        query.setParameter("name", name);
        MailPropertyKey propertyKey = null;
        try {
            propertyKey = (MailPropertyKey) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return propertyKey;
    }

}
