package com.frobom.sw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.service.MailAddressService;

@Component("mailAddressFormValidator")
public class MailAddressFormValidator implements Validator {

    @Autowired
    @Qualifier("mailAddressService")
    private MailAddressService mailAddressService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MailAddressService.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MailAddress mailAddress = (MailAddress) target;
        if (mailAddressService.findByAddress(mailAddress.getAddress()) != null) {
            errors.rejectValue("address", "address.alreadyExists", "Address already taken.");
        }
    }

}
