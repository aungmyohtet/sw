package com.frobom.sw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.frobom.sw.entity.AlertWordCountSetting;
import com.frobom.sw.service.AlertWordCountSettingService;

@Component("alertWordCountSettingFormValidator")
public class AlertWordCountSettingFormValidator implements Validator {

    @Autowired
    @Qualifier("alertWordCountSettingService")
    private AlertWordCountSettingService alertWordCountSettingService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AlertWordCountSetting.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AlertWordCountSetting alertWordCountSetting = (AlertWordCountSetting) target;
        if (alertWordCountSettingService.findByName(alertWordCountSetting.getName()) != null) {
            errors.rejectValue("name", "name.alreadyExists", "Setting name already exist.");
        }
    }

}
