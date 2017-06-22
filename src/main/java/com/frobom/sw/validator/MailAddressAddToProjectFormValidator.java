package com.frobom.sw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;
import com.frobom.sw.service.AddMailAddressToProjectService;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.ProjectService;

@Component("mailAddressAddToProjFormValidator")
public class MailAddressAddToProjectFormValidator implements Validator {

    @Autowired
    @Qualifier("addMailAddressToProjService")
    private AddMailAddressToProjectService service;

    public void setService(AddMailAddressToProjectService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MailAddressService.class.equals(clazz) && ProjectService.class.equals(clazz);
    }

    @Override
    public void validate(Object arg0, Errors arg1) {
    }

    public void validate(Object target1, Errors error1, Object target2, Errors error2) {
        Project project = (Project) target1;
        MailAddress mailAddress = (MailAddress) target2;
        if (service.IsExistsMailAddress(mailAddress, project)) {
            error1.rejectValue("name", "name.alreadyExists", "Project Name already taken.");
            error2.rejectValue("address", "address.alreadyExists", "Address already taken.");
        }
    }
}
