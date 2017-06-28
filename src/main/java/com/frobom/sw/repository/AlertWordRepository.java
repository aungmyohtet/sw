package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.AlertWord;
import com.frobom.sw.entity.AlertWord.Language;

public interface AlertWordRepository {

    void save(AlertWord alertWord);

    List<AlertWord> findAll();

    AlertWord findById(int id);

    AlertWord findByWordAndLanguage(String word, Language language);

    void update(AlertWord alertWord);

    void delete(int id);
}
