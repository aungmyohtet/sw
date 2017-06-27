package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.frobom.sw.entity.AlertWord;
import com.frobom.sw.entity.AlertWord.Language;
import com.frobom.sw.repository.AlertWordRepository;

@Service("alertWordService")
public class AlertWordServiceImpl implements AlertWordService {

    @Autowired
    private AlertWordRepository alertWordRepo;

    public void setAlertWordRepo(AlertWordRepository alertWordRepo) {
        this.alertWordRepo = alertWordRepo;
    }

    @Override
    @Transactional
    public void save(AlertWord alertWord) {
        alertWordRepo.save(alertWord);
    }

    @Override
    @Transactional
    public List<AlertWord> findAll() {
        return alertWordRepo.findAll();
    }

    @Override
    @Transactional
    public AlertWord findByWordAndLanguage(String word, Language language) {
        return alertWordRepo.findByWordAndLanguage(word, language);
    }

    @Override
    @Transactional
    public AlertWord findById(int id) {
        return alertWordRepo.findById(id);
    }

    @Override
    @Transactional
    public void update(AlertWord alertWord) {
       alertWordRepo.update(alertWord);
    }

    @Override
    @Transactional
    public void delete(int id) {
       alertWordRepo.delete(id);
    }

}
