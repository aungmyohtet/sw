package com.frobom.sw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;
import com.frobom.sw.service.AddMailAddressToProjectService;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.ProjectService;
import com.frobom.sw.validator.MailAddressAddToProjectFormValidator;

@Controller
public class MailAdddressAddToProjectController {

    @Autowired
    private MailAddressAddToProjectFormValidator validator;

    @Autowired
    private MailAddressService mailAddressService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AddMailAddressToProjectService addMailToProjService;

    public void setValidator(MailAddressAddToProjectFormValidator validator) {
        this.validator = validator;
    }

    public void setMailAddressService(MailAddressService mailAddressService) {
        this.mailAddressService = mailAddressService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setAddMailToProjService(AddMailAddressToProjectService addMailToProjService) {
        this.addMailToProjService = addMailToProjService;
    }

    @RequestMapping(value = "/projects/add/mailAddress", method = RequestMethod.GET)
    public String addMailAddressToProjectForm(Model model) {
        model.addAttribute("mailAddress", new MailAddress());
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        model.addAttribute("project", new Project());
        model.addAttribute("projects", projectService.findAll());
        return "add_MailAddress_To_Project";
    }

    @RequestMapping(value = "/projects/add/mailAddress", method = RequestMethod.POST)
    public String addMailAddressToProject(@Validated @ModelAttribute Project project, BindingResult projResult, @Validated @ModelAttribute MailAddress mailAddress,
            BindingResult mailResult, Model model) {

        if (projResult.hasErrors() || mailResult.hasErrors()) {
            model.addAttribute("projects", projectService.findAll());
            model.addAttribute("mailAddresses", mailAddressService.findAll());
            return "add_MailAddress_To_Project";
        }

        validator.validate(project, projResult, mailAddress, mailResult);
        if (projResult.hasErrors() || mailResult.hasErrors()) {
            model.addAttribute("projects", projectService.findAll());
            model.addAttribute("mailAddresses", mailAddressService.findAll());
            return "add_MailAddress_To_Project";
        }

        addMailToProjService.addMailAddressToProject(mailAddress.getAddress(), project.getName());
        return "redirect:/";

    }
}
