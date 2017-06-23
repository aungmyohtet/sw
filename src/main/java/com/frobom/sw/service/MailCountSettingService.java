package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.MailCountSetting;

public interface MailCountSettingService {

    void save(MailCountSetting mailCountSetting);

    List<MailCountSetting> findAll();

    MailCountSetting findById(int id);

    MailCountSetting findByName(String name);
}