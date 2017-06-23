package com.frobom.sw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.frobom.sw.entity.AlertWordCountSetting;
import com.frobom.sw.entity.MailCountSetting;
import com.frobom.sw.service.MailCountSettingService;

@Component("mailCountSettingFormValidator")
public class MailCountSettingFormValidator implements Validator {

    @Autowired
    @Qualifier("mailCountSettingService")
    private MailCountSettingService mailCountSettingService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AlertWordCountSetting.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MailCountSetting mailCountSetting = (MailCountSetting) target;
        if (mailCountSettingService.findByName(mailCountSetting.getName()) != null) {
            errors.rejectValue("name", "name.alreadyExists", "Setting name already exist.");
        }
    }

}
