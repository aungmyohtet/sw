package com.frobom.sw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.frobom.sw.entity.AlertWordRule;
import com.frobom.sw.service.AlertWordRuleService;
import com.frobom.sw.service.ProjectService;

@Component("alertWordRuleFormValidator")
public class AlertWordRuleFormValidator implements Validator {

    @Autowired
    @Qualifier("alertWordRuleService")
    private AlertWordRuleService alertWordRuleService;

    @Autowired
    @Qualifier("projectService")
    private ProjectService projectService;

    @Override
    public boolean supports(Class<?> calzz) {
        return AlertWordRule.class.equals(calzz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AlertWordRule alertWordRule = (AlertWordRule) target;
        int projectId = alertWordRule.getProject().getId();
        if (projectId == 0) {
            errors.rejectValue("project.id", "project.id.notSelected", "Select project.");
        }
        if (alertWordRuleService.findById(projectId) != null) {
            errors.rejectValue("project.id", "project.id.alreadyExists", "Project already exists.");
        }

    }

}
