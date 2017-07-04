package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.MailPropertyKey;
import com.frobom.sw.entity.MailPropertySetting;

public interface MailPropertyKeyRepository {

    void save(MailPropertyKey mailPropertyKey);

    List<MailPropertyKey> findAll();

    MailPropertyKey findById(int id);

    MailPropertyKey findByName(String name);

    void update(MailPropertyKey mailPropertyKey);

    void delete(int id);
}
