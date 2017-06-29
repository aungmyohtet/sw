package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.MailRawDataPath;
import com.frobom.sw.repository.MailRawDataPathRepository;

@Repository
public class MailRawDataPathRepositoryImpl implements MailRawDataPathRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(MailRawDataPath mailRawDataPath) {
        if (mailRawDataPath.getId() == null) {
            entityManager.persist(mailRawDataPath);
        } else {
            entityManager.merge(mailRawDataPath);
        }
    }

    @Override
    public List<MailRawDataPath> findAll() {
        return entityManager.createQuery("select e from MailRawDataPath e", MailRawDataPath.class).getResultList();
    }

    @Override
    public MailRawDataPath findById(int id) {
        return entityManager.find(MailRawDataPath.class, id);
    }

    @Override
    public void delete(MailRawDataPath path) {
        entityManager.remove(path);
    }

}
