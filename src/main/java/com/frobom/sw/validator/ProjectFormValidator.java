package com.frobom.sw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.ProjectService;

@Component("projectFormValidator")
public class ProjectFormValidator implements Validator {

    @Autowired
    @Qualifier("projectService")
    private ProjectService projectService;

    @Autowired
    @Qualifier("mailAddressService")
    private MailAddressService mailAddressService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Project.class.equals(clazz) && MailAddress.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Project project = (Project) target;
        if (projectService.findByName(project.getName()) != null) {
            errors.rejectValue("name", "name.alreadyExists", "Name already taken.");
        }
    }

    public void validate(Object target1, Object target2, Errors error) {
        MailAddress mailAddress = (MailAddress) target1;
        Project project = (Project) target2;
        if (projectService.IsExistsMailAddress(mailAddress, project)) {
            error.rejectValue("address", "address.alreadyExists", "Address already taken.");
        }
    }
}
