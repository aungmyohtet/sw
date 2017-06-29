package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.NotificationResult;
import com.frobom.sw.repository.NotificationResultRepository;

@Repository
public class NotificationResultRepositoryImpl implements NotificationResultRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(NotificationResult notificationResult) {
        entityManager.persist(notificationResult);
        entityManager.flush();
    }

    @Override
    public List<NotificationResult> findAll() {
        return entityManager.createQuery("select n from NotificationResult n", NotificationResult.class).getResultList();
    }

    @Override
    public NotificationResult findById(int id) {
        return entityManager.find(NotificationResult.class, id);
    }

    @Override
    public void delete(NotificationResult notiResult) {
        entityManager.remove(notiResult);
    }

}
