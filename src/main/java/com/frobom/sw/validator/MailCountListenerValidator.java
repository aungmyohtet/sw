package com.frobom.sw.validator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.frobom.sw.entity.MailCountListener;
import com.frobom.sw.entity.MailPropertySetting;
import com.frobom.sw.service.MailCountListenerService;
import com.frobom.sw.web.HomeController;

@Component("mailCountListenerValidator")
public class MailCountListenerValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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
        String address = mailCountListener.getMailAddress().getAddress();

        if (address.equals("0")) {
            errors.rejectValue("mailAddress.address", "mailAddress.address.alreadyExists", "Choose Mail Address ");
        }

        List<MailCountListener> mailCountListenerList = mailCountListenerService.findAll();
        for (MailCountListener m : mailCountListenerList) {
            if (m.getMailAddress().getAddress().equals(address)) {
                errors.rejectValue("mailAddress.address", "mailAddress.address.alreadyExists", "Duplicate");
            }
        }

    }

}
