package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.Mail;

public interface MailRepository {

    List<Mail> findAll();

    List<Mail> findAllByFetchingSubEntities();

    void delete(Mail mail);
}
