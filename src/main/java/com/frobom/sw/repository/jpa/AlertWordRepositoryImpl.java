package com.frobom.sw.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.sw.entity.AlertWord;
import com.frobom.sw.entity.AlertWord.Language;
import com.frobom.sw.repository.AlertWordRepository;

@Repository
public class AlertWordRepositoryImpl implements AlertWordRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(AlertWord alertWord) {
        if (alertWord.getId() == null) {
            entityManager.persist(alertWord);
        } else {
            entityManager.merge(alertWord);
        }
    }

    @Override
    public List<AlertWord> findAll() {
        return entityManager.createQuery("select a from AlertWord a", AlertWord.class).getResultList();
    }

    @Override
    public AlertWord findById(int id) {
        return entityManager.find(AlertWord.class, id);
    }

    @Override
    public AlertWord findByWordAndLanguage(String word, Language language) {
        AlertWord alertWord = null;
        try {
            Query q = entityManager.createQuery("select a from AlertWord a where a.word=?1 and a.language=?2");
            q.setParameter(1, word);
            q.setParameter(2, language);
            alertWord = (AlertWord) q.getSingleResult();
        } catch (NoResultException e) {

        }
        return alertWord;
    }

}
