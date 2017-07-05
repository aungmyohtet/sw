package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.Mail;
import com.frobom.sw.repository.MailRepository;

@Repository
public class MailRepositoryImpl implements MailRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Mail> findAll() {
        return entityManager.createQuery("select m from Mail m", Mail.class).getResultList();
    }

    @Override
    public List<Mail> findAllByFetchingSubEntities() {
        Query query = entityManager.createQuery("SELECT m FROM Mail m "
                + "JOIN FETCH m.mailAddress addr "
                + "JOIN FETCH addr.projects prjs");
        return query.getResultList();
    }

    @Override
    public void delete(Mail mail) {
        entityManager.remove(mail);
    }
}
