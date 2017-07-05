package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.repository.MailAddressRepository;

@Repository
public class MailAddressRepositoryImpl implements MailAddressRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(MailAddress mailAddress) {
        if (mailAddress.getId() == null) {
            entityManager.persist(mailAddress);
        } else {
            entityManager.merge(mailAddress);
        }
    }

    @Override
    public List<MailAddress> findAll() {
        return entityManager.createQuery("select m from MailAddress m", MailAddress.class).getResultList();
    }

    @Override
    public MailAddress findById(int id) {
        return entityManager.find(MailAddress.class, id);
    }

    @Override
    public MailAddress findByAddress(String address) {
        Query query = entityManager.createQuery("from MailAddress m where m.address=:address", MailAddress.class);
        query.setParameter("address", address);
        MailAddress mailAddress = null;
        try {
            mailAddress = (MailAddress) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return mailAddress;
    }

    @Override
    public MailAddress findByName(String name) {
        MailAddress mailAddress = null;
        try {
            Query q = entityManager.createQuery("select m from MailAddress m where m.name=?");
            q.setParameter(1, name);
            mailAddress = (MailAddress) q.getSingleResult();
        } catch (NoResultException e) {

        }
        return mailAddress;
    }

    @Override
    public void update(MailAddress mailAddress) {
        Query query= entityManager.createQuery("update MailAddress m set m.name=:name, m.address=:address where m.id=:id");
        query.setParameter("id", mailAddress.getId());
        query.setParameter("name", mailAddress.getName());
        query.setParameter("address", mailAddress.getAddress());
        query.executeUpdate();
    }

    @Override
    public void delete(MailAddress mailAddress) {
       entityManager.remove(mailAddress);
    }
}
