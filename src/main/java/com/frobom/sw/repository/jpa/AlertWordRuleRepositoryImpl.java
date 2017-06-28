package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.AlertWordRule;
import com.frobom.sw.entity.Project;
import com.frobom.sw.repository.AlertWordRuleRepository;

@Repository
public class AlertWordRuleRepositoryImpl implements AlertWordRuleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(AlertWordRule alertWorldRule) {
        entityManager.persist(alertWorldRule);
    }

    @Override
    public List<AlertWordRule> findAll() {
        return entityManager.createQuery("SELECT a FROM AlertWordRule a", AlertWordRule.class).getResultList();
    }

    @Override
    public AlertWordRule findById(int id) {
        return entityManager.find(AlertWordRule.class, id);
    }

    @Override
    public Integer getThresholdByProject(Project project) {
        String queryStatement = "SELECT a.threshold FROM AlertWordRule a WHERE a.project = ?1";
        Query query = entityManager.createQuery(queryStatement, AlertWordRule.class).setParameter(1, project);
        try {
            return (Integer) query.getSingleResult();
        } catch (NoResultException e) {
            // need to log?
        }
        return 0;
    }

    @Override
    public AlertWordRule findByProject(Project project) {
        String queryStatement = "SELECT a FROM AlertWordRule a WHERE a.project = ?1";
        Query query = entityManager.createQuery(queryStatement, AlertWordRule.class).setParameter(1, project);
        AlertWordRule alertWordRule = null;
        try {
            alertWordRule = (AlertWordRule) query.getSingleResult();
        } catch (NoResultException e) {
            // need to log?
        }
        return alertWordRule;
    }

    @Override
    public void update(AlertWordRule alertWorldRule) {
        entityManager.merge(alertWorldRule);
    }

    @Override
    public void delete(AlertWordRule alertWorldRule) {
        entityManager.remove(alertWorldRule);
    }

}
