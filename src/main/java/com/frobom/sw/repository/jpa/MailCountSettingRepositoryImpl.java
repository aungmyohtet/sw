package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.MailCountSetting;
import com.frobom.sw.repository.MailCountSettingRepository;

@Repository
public class MailCountSettingRepositoryImpl implements MailCountSettingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(MailCountSetting mailCountSetting) {
        entityManager.persist(mailCountSetting);
    }

    @Override
    public List<MailCountSetting> findAll() {
        return entityManager.createQuery("SELECT m FROM MailCountSetting m", MailCountSetting.class).getResultList();
    }

    @Override
    public MailCountSetting findById(int id) {
        return entityManager.find(MailCountSetting.class, id);
    }

    @Override
    public MailCountSetting findByName(String name) {
        Query query = entityManager.createQuery("from MailCountSetting setting where setting.name=:name", MailCountSetting.class);
        query.setParameter("name", name);
        MailCountSetting mailCountSetting = null;
        try {
            mailCountSetting = (MailCountSetting) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return mailCountSetting;
    }

}
