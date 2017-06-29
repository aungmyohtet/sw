package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.MailCount;
import com.frobom.sw.repository.MailCountRepository;

@Service("mailCountService")
public class MailCountServiceImpl implements MailCountService {

    @Autowired
    private MailCountRepository mailCountRepo;

    public void setMailCountRepo(MailCountRepository mailCountRepo) {
        this.mailCountRepo = mailCountRepo;
    }

    @Override
    @Transactional
    public List<MailCount> findAll() {
        return mailCountRepo.findAll();
    }

}
