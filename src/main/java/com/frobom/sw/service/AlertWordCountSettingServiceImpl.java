package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.AlertWordCountSetting;
import com.frobom.sw.repository.AlertWordCountSettingRepository;

@Service("alertWordCountSettingService")
public class AlertWordCountSettingServiceImpl implements AlertWordCountSettingService {

    @Autowired
    AlertWordCountSettingRepository alertWordCountSettingRepo;

    public void setAlertWordCountSettingRepo(AlertWordCountSettingRepository alertWordCountSettingRepo) {
        this.alertWordCountSettingRepo = alertWordCountSettingRepo;
    }

    @Override
    @Transactional
    public void add(AlertWordCountSetting alertWordCountSetting) {
        alertWordCountSettingRepo.add(alertWordCountSetting);
    }

    @Override
    @Transactional
    public List<AlertWordCountSetting> findAll() {
        return alertWordCountSettingRepo.findAll();
    }

    @Override
    @Transactional
    public AlertWordCountSetting findById(int id) {
        return alertWordCountSettingRepo.findById(id);
    }

    @Override
    @Transactional
    public AlertWordCountSetting findByName(String name) {
        return alertWordCountSettingRepo.findByName(name);
    }

    @Override
    @Transactional
    public void update(AlertWordCountSetting alertWordCountSetting) {
        AlertWordCountSetting oldSetting = alertWordCountSettingRepo.findById(alertWordCountSetting.getId());
        oldSetting.setName(alertWordCountSetting.getName());
        oldSetting.setValue(alertWordCountSetting.getValue());
    }

    @Override
    @Transactional
    public void remove(int id) {
        alertWordCountSettingRepo.delete(id);
    }

}
