package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.AlertWordRule;
import com.frobom.sw.entity.Project;
import com.frobom.sw.repository.AlertWordRuleRepository;
import com.frobom.sw.repository.ProjectRepository;

@Service("alertWordRuleService")
public class AlertWordRuleServiceImpl implements AlertWordRuleService {

    @Autowired
    private AlertWordRuleRepository alertWordRuleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public void setAlertWordRuleRepository(AlertWordRuleRepository alertWordRuleRepository) {
        this.alertWordRuleRepository = alertWordRuleRepository;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public void add(AlertWordRule alertWorldRule) {
        alertWordRuleRepository.save(alertWorldRule);
    }

    @Override
    @Transactional
    public List<AlertWordRule> findAll() {
        return alertWordRuleRepository.findAll();
    }

    @Override
    @Transactional
    public AlertWordRule findById(int id) {
        return alertWordRuleRepository.findById(id);
    }

    @Override
    @Transactional
    public Integer getThresholdByProject(Project project) {
        return alertWordRuleRepository.getThresholdByProject(project);
    }

    @Override
    @Transactional
    public void add(int projectId, int threshold) {
        Project project = projectRepository.findById(projectId);
        if (project != null) {
            AlertWordRule alertWordRule = new AlertWordRule();
            alertWordRule.setProject(project);
            alertWordRule.setThreshold(threshold);
            alertWordRuleRepository.save(alertWordRule);
            System.out.println("project of alertWordRule : " + alertWordRule.getProject().getName());
        }
    }

    @Override
    @Transactional
    public AlertWordRule findByProject(Project project) {
        return alertWordRuleRepository.findByProject(project);
    }

    @Override
    @Transactional
    public void delete(int id) {
        alertWordRuleRepository.delete(alertWordRuleRepository.findById(id));
    }

    @Override
    @Transactional
    public void update(AlertWordRule alertWordRule) {
        alertWordRuleRepository.update(alertWordRule);
    }

}
