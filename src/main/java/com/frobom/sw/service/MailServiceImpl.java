package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.Mail;
import com.frobom.sw.repository.MailRepository;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private MailRepository mailRepo;

    public void setMailRepo(MailRepository mailRepo) {
        this.mailRepo = mailRepo;
    }

    @Override
    @Transactional
    public List<Mail> findAll() {
        return mailRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mail> findAllByFetchingSubEntities() {
        return mailRepo.findAllByFetchingSubEntities();
    }

}
