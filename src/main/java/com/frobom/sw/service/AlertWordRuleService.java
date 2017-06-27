package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.AlertWordRule;
import com.frobom.sw.entity.Project;

public interface AlertWordRuleService {

    void add(AlertWordRule alertWorldRule);

    List<AlertWordRule> findAll();

    AlertWordRule findById(int id);

    AlertWordRule findByProject(Project project);

    Integer getThresholdByProject(Project project);

    void add(int projectId, int threshold);
}
