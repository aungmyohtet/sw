package com.frobom.sw.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.frobom.sw.entity.MailPropertySetting;
import com.frobom.sw.repository.MailPropertySettingRepository;

@Service("mailPropertySettingService")

public class MailPropertySettingServiceImpl implements MailPropertySettingService {

    @Autowired
    private MailPropertySettingRepository mailPropertySettingRepository;

    public void setMailPropertySettingRepository(MailPropertySettingRepository mailPropertySettingRepository) {
        this.mailPropertySettingRepository = mailPropertySettingRepository;
    }

    @Override
    @Transactional
    public void add(MailPropertySetting mailPropertySetting) {

        mailPropertySettingRepository.save(mailPropertySetting);
    }

    @Override
    public List<MailPropertySetting> findAll() {
        return mailPropertySettingRepository.findAll();
    }

    @Override
    public MailPropertySetting findById(int id) {

        return mailPropertySettingRepository.findById(id);
    }

    @Override
    @Transactional
    public void update(MailPropertySetting mailPropertySetting) {
        mailPropertySettingRepository.update(mailPropertySetting);
    }

    @Override
    @Transactional
    public void delete(int mailPropertyKeyId, int mailAddressId) {
        mailPropertySettingRepository.delete(mailPropertyKeyId, mailAddressId);
    }

    @Override
    @Transactional
    public void delete(int mailPropertyKeyId) {
        mailPropertySettingRepository.delete(mailPropertyKeyId);
    }

    @Override
    @Transactional
    public MailPropertySetting findByIds(int id, int mailAddressId) {
        return mailPropertySettingRepository.findByIds(id, mailAddressId);
    }
}
