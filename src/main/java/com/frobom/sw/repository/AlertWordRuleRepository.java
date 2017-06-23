package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.AlertWordRule;
import com.frobom.sw.entity.Project;

public interface AlertWordRuleRepository {

    void save(AlertWordRule alertWorldRule);

    List<AlertWordRule> findAll();

    AlertWordRule findById(int id);

    AlertWordRule findByProject(Project project);

    Integer getThresholdByProject(Project project);
}
