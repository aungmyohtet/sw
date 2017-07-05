package com.frobom.sw.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.frobom.sw.entity.MailCountSetting;
import com.frobom.sw.service.MailCountSettingService;

@Component("mailCountSettingFormValidator")
public class MailCountSettingFormValidator implements Validator {

    @Autowired
    @Qualifier("mailCountSettingService")
    private MailCountSettingService mailCountSettingService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MailCountSetting.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MailCountSetting mailCountSetting = (MailCountSetting) target;
        String name = mailCountSetting.getName();
        if (!(mailCountSettingService.getMailCountSettingNames().contains(name))) {
            errors.rejectValue("name", "name.alreadyExists", "Select name");
        }
        if (mailCountSetting.getValue().equals(null)) {
            errors.rejectValue("value", "value.alreadyExists", "Enter value");
        }
    }
}
