package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.MailRawDataPath;

public interface MailRawDataPathRepository {

    void save(MailRawDataPath mailRawDataPath);

    List<MailRawDataPath> findAll();

    MailRawDataPath findById(int id);

    void delete(MailRawDataPath path);
}
