package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.AlertWordRule;
import com.frobom.sw.entity.MailCountRule;
import com.frobom.sw.entity.Project;
import com.frobom.sw.repository.MailCountRuleRepository;

@Repository
public class MailCountRuleRepositoryImpl implements MailCountRuleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(MailCountRule mailCountRule) {
        entityManager.persist(mailCountRule);
        entityManager.flush();
    }

    @Override
    public List<MailCountRule> findAll() {
        return entityManager.createQuery("select m from MailCountRule m", MailCountRule.class).getResultList();
    }

    @Override
    public MailCountRule findById(int id) {
        return entityManager.find(MailCountRule.class, id);
    }

    @Override
    public MailCountRule findByProject(Project project) {
        String queryStatement = "SELECT m FROM MailCountRule m WHERE m.project = ?1";
        Query query = entityManager.createQuery(queryStatement, MailCountRule.class).setParameter(1, project);
        MailCountRule mailCountRule = null;
        try {
            mailCountRule = (MailCountRule) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return mailCountRule;
    }

    @Override
    public void update(MailCountRule mailCountRule) {
        entityManager.merge(mailCountRule);
    }

    @Override
    public Integer getThresholdByProject(Project project) {
        String queryStatement = "SELECT m.threshold FROM MailCountRule m WHERE m.project = ?1";
        Query query = entityManager.createQuery(queryStatement, AlertWordRule.class).setParameter(1, project);
        return (Integer) query.getSingleResult();
    }

}
