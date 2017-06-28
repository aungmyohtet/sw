package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.Mail;

public interface MailRepository {

    List<Mail> findAll();

}
