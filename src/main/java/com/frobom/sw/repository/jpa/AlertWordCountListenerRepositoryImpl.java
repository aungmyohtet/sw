package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.AlertWordCountListener;
import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.repository.AlertWordCountListenerRepository;

@Repository
public class AlertWordCountListenerRepositoryImpl implements AlertWordCountListenerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(AlertWordCountListener alertWordCountListener) {
        entityManager.persist(alertWordCountListener);
    }

    @Override
    public List<AlertWordCountListener> findAll() {
        return entityManager.createQuery("SELECT a FROM AlertWordCountListener a", AlertWordCountListener.class).getResultList();
    }

    @Override
    public AlertWordCountListener findById(int id) {
        return entityManager.find(AlertWordCountListener.class, id);
    }

    @Override
    public AlertWordCountListener findByMailAddress(MailAddress mailAddress) {
        Query query = entityManager.createQuery("SELECT a FROM AlertWordCountListener a WHERE mailAddress=:address", AlertWordCountListener.class);
        query.setParameter("address", mailAddress);
        AlertWordCountListener alertWordCountListener = null;
        try {
            alertWordCountListener = (AlertWordCountListener) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return alertWordCountListener;
    }

    @Override
    public void delete(AlertWordCountListener alertWordCountListener) {
        entityManager.remove(alertWordCountListener);
    }

}
