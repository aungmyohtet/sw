package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.MailPropertySetting;

public interface MailPropertySettingRepository {

    void save(MailPropertySetting mailPropertySetting);

    List<MailPropertySetting> findAll();

    MailPropertySetting findById(int id);

    MailPropertySetting findByIds(int mailPropertyKeyId, int mailAddressId);

    void update(MailPropertySetting mailPropertySetting);

    void delete(int mailPropertyKeyId, int mailAddressId);

    void delete(int mailPropertyKeyId);
  
    void delete(MailPropertySetting setting);

}
