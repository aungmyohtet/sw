package com.frobom.sw.service;

import java.util.List;
import com.frobom.sw.entity.MailCountRule;
import com.frobom.sw.entity.Project;

public interface MailCountRuleService {

    void add(MailCountRule mailCountRule);

    public List<MailCountRule> findAll();

    MailCountRule findById(int id);

    void update(MailCountRule mailCountRule);

    void delete(int id);

    void add(int projectId, int threshold);

    MailCountRule findByProject(Project project);

}
