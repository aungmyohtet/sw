package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.MailPropertyKey;

public interface MailPropertyKeyRepository {

    void save(MailPropertyKey mailPropertyKey);

    List<MailPropertyKey> findAll();

    MailPropertyKey findById(int id);

    MailPropertyKey findByName(String name);

    void delete(int id);
}
