package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.MailCountSetting;
import com.frobom.sw.repository.MailCountSettingRepository;

@Service("mailCountSettingService")
public class MailCountSettingServiceImpl implements MailCountSettingService {

    @Autowired
    private MailCountSettingRepository mailCountSettingRepo;

    public void setMailCountSettingRepo(MailCountSettingRepository mailCountSettingRepo) {
        this.mailCountSettingRepo = mailCountSettingRepo;
    }

    @Override
    @Transactional
    public void save(MailCountSetting mailCountSetting) {
        mailCountSettingRepo.save(mailCountSetting);
    }

    @Override
    @Transactional
    public List<MailCountSetting> findAll() {
        return mailCountSettingRepo.findAll();
    }

    @Override
    @Transactional
    public MailCountSetting findById(int id) {
        return mailCountSettingRepo.findById(id);
    }

    @Override
    @Transactional
    public MailCountSetting findByName(String name) {
        return mailCountSettingRepo.findByName(name);
    }

    @Override
    @Transactional
    public void delete(int id) {
        mailCountSettingRepo.delete(mailCountSettingRepo.findById(id));
    }

    @Override
    @Transactional
    public void update(MailCountSetting mailCountSetting) {
        mailCountSettingRepo.update(mailCountSetting);
    }

}
