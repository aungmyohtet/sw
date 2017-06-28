package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.MailCountRule;
import com.frobom.sw.entity.Project;
import com.frobom.sw.repository.MailCountRuleRepository;

@Service("mailCountRuleService")
public class MailCountRuleServiceImpl implements MailCountRuleService {

    @Autowired
    private MailCountRuleRepository mailCountRuleRepository;

    public void setMailCountRuleRepository(MailCountRuleRepository mailCountRuleRepository) {
        this.mailCountRuleRepository = mailCountRuleRepository;
    }

    @Autowired
    private ProjectService projectService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    @Transactional
    public void add(MailCountRule mailCountRule) {
        Project project = projectService.findById(mailCountRule.getProject().getId());
        mailCountRule.setProject(project);
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

}
