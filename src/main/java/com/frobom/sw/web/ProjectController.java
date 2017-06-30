package com.frobom.sw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.frobom.sw.entity.AlertWordRule;
import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;
import com.frobom.sw.service.AlertWordRuleService;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.ProjectService;
import com.frobom.sw.validator.ProjectFormValidator;

@Controller
public class ProjectController {

    String projName;

    @Autowired
    @Qualifier("projectFormValidator")
    private ProjectFormValidator projectFormValidator;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MailAddressService mailAddressService;

    @Autowired
    private AlertWordRuleService alertWordRuleService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setMailAddressService(MailAddressService mailAddressService) {
        this.mailAddressService = mailAddressService;
    }

    public void setAlertWordRuleService(AlertWordRuleService alertWordRuleService) {
        this.alertWordRuleService = alertWordRuleService;
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String showProjectList(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("projects", projectService.findAll());
        return "projects";
    }

    @RequestMapping(value = "/projects/add", method = RequestMethod.POST)
    public String saveProject(@Validated @ModelAttribute Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("projects", projectService.findAll());
            return "projects";
        }
        projectFormValidator.validate(project, result);
        if (result.hasErrors()) {
            model.addAttribute("projects", projectService.findAll());
            return "projects";
        }
        projectService.save(project);
        model.addAttribute("projects", this.projectService.findAll());
        return "redirect:/projects";
    }

    @RequestMapping(value = "/projects/{id}/delete", method = RequestMethod.GET)
    public String deleteProject(@PathVariable("id") int id, Model model) {
        projectService.delete(id);
        return "redirect:/projects";
    }

    @RequestMapping(value = "/projects/{id}/setting", method = RequestMethod.GET)
    public String setting(@PathVariable("id") int id, Model model) {
        // for project form
        model.addAttribute("project", projectService.findById(id));
        // for mail address form
        model.addAttribute("address", new MailAddress());
        model.addAttribute("addressList", mailAddressService.findAll());
        model.addAttribute("addresses", projectService.findMailAddressesByID(id));
        // for alert word rule form
        model.addAttribute("newAlertWordRule", new AlertWordRule());
        model.addAttribute("alertWordRule", alertWordRuleService.findByProject(projectService.findById(id)));
        return "project_setting";
    }

    @RequestMapping(value = "/projects/{id}/update", method = RequestMethod.POST)
    public String updateProject(@Validated @ModelAttribute("update_form") Project project, BindingResult result, Model model) {
        // if (result.hasErrors()) {
        // return "project_setting";
        // }
        // if (projName.equals(project.getName())) {
        // return "redirect:/projects/{id}/setting";
        // } else {
        // projectFormValidator.validate(project, result);
        // if (result.hasErrors()) {
        // return "project_setting";
        // }
        // }
        projectService.update(project);
        // model.addAttribute("address", new MailAddress());
        // model.addAttribute("addressList", mailAddressService.findAll());
        // model.addAttribute("addresses", projectService.findMailAddressesByID(project.getId()));
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{id}/addresses/add", method = RequestMethod.POST)
    public String addMailAddressToProject(@PathVariable("id") int id, @Validated @ModelAttribute("add_form") MailAddress mailAddress, BindingResult result, Model model) {
        // if (result.hasErrors()) {
        // model.addAttribute("mailAddresses", mailAddressService.findAll());
        // model.addAttribute("addresses", projectService.findMailAddressesByID(id));
        // return "add_MailAddress_To_Project";
        // }
        // projectFormValidator.validate(mailAddress, result);
        // if (result.hasErrors()) {
        // model.addAttribute("mailAddresses", mailAddressService.findAll());
        // model.addAttribute("addresses", projectService.findMailAddressesByID(id));
        // return "add_MailAddress_To_Project";
        // }
        projectService.addMailAddressToProject(mailAddress.getAddress(), id);
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{pid}/addresses/{mid}/delete", method = RequestMethod.GET)
    public String deleteMailAddress(@PathVariable("pid") int pid, @PathVariable("mid") int mid, Model model) {
        projectService.deleteMailAddress(pid, mid);
        return "redirect:/projects/{pid}/setting";
    }

    @RequestMapping(value = "/projects/{id}/alertwordrule/add", method = RequestMethod.POST)
    public String addAlertWordRule(@PathVariable("id") int id, @Validated @ModelAttribute("add_alert_form") AlertWordRule alertWordRule, BindingResult result, Model model) {
        model.addAttribute("alertWordRuleList", alertWordRuleService.findAll());
        model.addAttribute("projects", projectService.findAll());
        // if (result.hasErrors()) {
        // return "addAlertWordRule";
        // }
        //
        // alertWordRuleFormValidator.validate(alertWordRule, result);
        // if (result.hasErrors()) {
        // return "addAlertWordRule";
        // }
        alertWordRuleService.add(id, alertWordRule.getThreshold());
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{pid}/alertwordrule/{rid}/update", method = RequestMethod.GET)
    public String updateAlertWordRule(@Validated @ModelAttribute("add_alert_form") AlertWordRule alertWordRule, BindingResult result, Model model) {
        // if (result.hasErrors()) {
        // return "update_alertwordrule";
        // }
        alertWordRuleService.update(alertWordRule);
        return "redirect:/projects/{pid}/setting";
    }

    @RequestMapping(value = "/projects/{pid}/alertwordrule/{rid}/delete", method = RequestMethod.GET)
    public String deleteAlertWordRule(@PathVariable("pid") int pid, @PathVariable("rid") int rid, Model model) {
        AlertWordRule alertWordRule = alertWordRuleService.findById(rid);
        // if (alertWordRule == null) {
        // return "redirect:/bad/request";
        // }
        alertWordRuleService.delete(rid);
        return "redirect:/projects/{pid}/setting";
    }
}
