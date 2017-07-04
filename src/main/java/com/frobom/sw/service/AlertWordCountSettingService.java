package com.frobom.sw.service;

import java.util.ArrayList;
import java.util.List;

import com.frobom.sw.entity.AlertWordCountSetting;

public interface AlertWordCountSettingService {

    void add(AlertWordCountSetting alertWordCountSetting);

    List<AlertWordCountSetting> findAll();

    AlertWordCountSetting findById(int id);

    AlertWordCountSetting findByName(String name);

    void remove(int id);

    void update(AlertWordCountSetting alertWordCountSetting);

    ArrayList<String> getAlertWordCountSettingNames();
}
