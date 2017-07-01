package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.Mail;

public interface MailService {

    List<Mail> findAll();

    List<Mail> findAllByFetchingSubEntities();

}
