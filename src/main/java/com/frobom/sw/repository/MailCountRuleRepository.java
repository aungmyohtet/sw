package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.MailCountRule;
import com.frobom.sw.entity.Project;

public interface MailCountRuleRepository {

    void add(MailCountRule mailCountRule);

    List<MailCountRule> findAll();

    MailCountRule findById(int id);

    MailCountRule findByProject(Project project);

    void update(MailCountRule mailCountRule);

    Integer getThresholdByProject(Project project);
}
