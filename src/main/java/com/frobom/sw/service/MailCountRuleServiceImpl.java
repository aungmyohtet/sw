package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.MailCountRule;
import com.frobom.sw.entity.Project;
import com.frobom.sw.repository.MailCountRuleRepository;
import com.frobom.sw.repository.ProjectRepository;

@Service("mailCountRuleService")
public class MailCountRuleServiceImpl implements MailCountRuleService {

    @Autowired
    private MailCountRuleRepository mailCountRuleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public void setMailCountRuleRepository(MailCountRuleRepository mailCountRuleRepository) {
        this.mailCountRuleRepository = mailCountRuleRepository;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public void add(MailCountRule mailCountRule) {
        mailCountRuleRepository.add(mailCountRule);
    }

    @Override
    @Transactional
    public List<MailCountRule> findAll() {
        return mailCountRuleRepository.findAll();
    }

    @Override
    @Transactional
    public MailCountRule findById(int id) {
        return mailCountRuleRepository.findById(id);
    }

    @Override
    @Transactional
    public void update(MailCountRule mailCountRule) {
        mailCountRuleRepository.update(mailCountRule);
    }

    @Override
    @Transactional
    public void delete(int id) {
        mailCountRuleRepository.delete(mailCountRuleRepository.findById(id));
    }

    @Override
    @Transactional
    public void add(int projectId, int threshold) {
        Project project = projectRepository.findById(projectId);
        if (project != null) {
            MailCountRule mailCountRule = new MailCountRule();
            mailCountRule.setProject(project);
            mailCountRule.setThreshold(threshold);
            mailCountRuleRepository.add(mailCountRule);
        }
    }

    @Override
    @Transactional
    public MailCountRule findByProject(Project project) {
        return mailCountRuleRepository.findByProject(project);
    }

}
