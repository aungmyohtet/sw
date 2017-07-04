package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.MailPropertySetting;

public interface MailPropertySettingService {

    void add(MailPropertySetting mailPropertySetting);

    public List<MailPropertySetting> findAll();

    public MailPropertySetting findById(int id);

    void update(MailPropertySetting mailPropertySetting);

    public void delete(int mailPropertyKeyId);

    void delete(int mailPropertyKeyId, int mailAddressId);

    MailPropertySetting findByIds(int id, int mailAddressId);
}
