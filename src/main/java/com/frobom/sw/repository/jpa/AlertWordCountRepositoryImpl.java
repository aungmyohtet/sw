package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.AlertWordCount;
import com.frobom.sw.repository.AlertWordCountRepository;

@Repository
public class AlertWordCountRepositoryImpl implements AlertWordCountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<AlertWordCount> findAll() {
        return entityManager.createQuery("select a from AlertWordCount a", AlertWordCount.class).getResultList();
    }

    @Override
    public void delete(AlertWordCount alertWordCount) {
        entityManager.remove(alertWordCount);
    }
}
