package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.AlertWord;
import com.frobom.sw.entity.AlertWord.Language;

public interface AlertWordService {

    void save(AlertWord alertWord);

    List<AlertWord> findAll();

    AlertWord findByWordAndLanguage(String word, Language language);

    AlertWord findById(int id);

    void update(AlertWord alertWord);

    void delete(int id);
}
