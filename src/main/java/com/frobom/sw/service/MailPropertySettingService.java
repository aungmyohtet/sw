package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.MailPropertySetting;

public interface MailPropertySettingService {

    void add(MailPropertySetting mailPropertySetting);

    public List<MailPropertySetting> findAll();

    public MailPropertySetting findById(int id);
}
