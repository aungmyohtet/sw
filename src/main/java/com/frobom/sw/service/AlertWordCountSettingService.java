package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.AlertWordCountSetting;

public interface AlertWordCountSettingService {

    void add(AlertWordCountSetting alertWordCountSetting);

    List<AlertWordCountSetting> findAll();

    AlertWordCountSetting findById(int id);

    AlertWordCountSetting findByName(String name);
}
