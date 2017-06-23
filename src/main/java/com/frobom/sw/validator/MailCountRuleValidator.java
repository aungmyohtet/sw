package com.frobom.sw.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.frobom.sw.entity.MailCountRule;
import com.frobom.sw.service.MailCountRuleService;

@Component("mailCountRuleValidator")
public class MailCountRuleValidator implements Validator {

    @Autowired
    @Qualifier("mailCountRuleService")
    private MailCountRuleService mailCountRuleService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MailCountRule.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MailCountRule mailCountRule = (MailCountRule) target;
        int projectId = mailCountRule.getProject().getId();

        if (projectId == 0) {
            errors.rejectValue("project.id", "project.id.alreadyExists", "Select Project");
        }

        List<MailCountRule> mailCountRuleList = mailCountRuleService.findAll();
        for (MailCountRule m : mailCountRuleList) {
            if (m.getProject().getId() == projectId) {
                errors.rejectValue("project.id", "project.id.alreadyExists", "Project Already Exists");
            }
        }
    }

}
