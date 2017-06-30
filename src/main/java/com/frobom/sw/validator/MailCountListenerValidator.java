package com.frobom.sw.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.frobom.sw.entity.MailCountListener;
import com.frobom.sw.service.MailCountListenerService;

@Component("mailCountListenerValidator")
public class MailCountListenerValidator implements Validator {

    @Autowired
    @Qualifier("mailCountListenerService")
    private MailCountListenerService mailCountListenerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MailCountListener.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MailCountListener mailCountListener = (MailCountListener) target;
        int mailAddressId = mailCountListener.getMailAddress().getId();

        if (mailAddressId == 0) {
            errors.rejectValue("mailAddress.id", "mailAddress.id.alreadyExists", "Choose Mail Address ");
        }

        List<MailCountListener> mailCountListenerList = mailCountListenerService.findAll();
        for (MailCountListener m : mailCountListenerList) {
            if (m.getMailAddress().getId() == mailAddressId) {
                errors.rejectValue("mailAddress.id", "mailAddress.id.alreadyExists", "Duplicate");
            }
        }
    }
}
