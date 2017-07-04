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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frobom.sw.entity.AlertWordRule;
import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.MailCountRule;
import com.frobom.sw.entity.Project;
import com.frobom.sw.service.AlertWordRuleService;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.MailCountRuleService;
import com.frobom.sw.service.ProjectService;
import com.frobom.sw.validator.ProjectFormValidator;

@Controller
public class ProjectController {

    private String projName;

    @Autowired
    @Qualifier("projectFormValidator")
    private ProjectFormValidator projectFormValidator;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MailAddressService mailAddressService;

    @Autowired
    private AlertWordRuleService alertWordRuleService;

    @Autowired
    private MailCountRuleService mailCountRuleService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setMailAddressService(MailAddressService mailAddressService) {
        this.mailAddressService = mailAddressService;
    }

    public void setAlertWordRuleService(AlertWordRuleService alertWordRuleService) {
        this.alertWordRuleService = alertWordRuleService;
    }

    public void setMailCountRuleService(MailCountRuleService mailCountRuleService) {
        this.mailCountRuleService = mailCountRuleService;
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
    public String showSettingPage(@PathVariable("id") int id, Model model) {

        projName = projectService.findById(id).getName();
        if (!model.containsAttribute("project")) {
            model.addAttribute("project", projectService.findById(id));
        }
        if (!model.containsAttribute("mailAddress")) {
            model.addAttribute("mailAddress", new MailAddress());
        }
        model.addAttribute("mailAddressList", mailAddressService.findAll());
        model.addAttribute("mailAddresses", projectService.findMailAddressesByID(id));

        if (!model.containsAttribute("newAlertWordRule")) {
            model.addAttribute("newAlertWordRule", new AlertWordRule());
        }
        if (!model.containsAttribute("alertWordRule")) {
            model.addAttribute("alertWordRule", alertWordRuleService.findByProject(projectService.findById(id)));
        }
        if (!model.containsAttribute("newMailCountRule")) {
            model.addAttribute("newMailCountRule", new MailCountRule());
        }
        if (!model.containsAttribute("mailCountRule")) {
            model.addAttribute("mailCountRule", mailCountRuleService.findByProject(projectService.findById(id)));
        }
        return "project_setting";
    }

    @RequestMapping(value = "/projects/{id}/update", method = RequestMethod.POST)
    public String updateProject(@Validated @ModelAttribute Project project, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("project", project);
        if (projName.equals(project.getName())) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            return "redirect:/projects/{id}/setting";
        }
        projectFormValidator.validate(project, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            return "redirect:/projects/{id}/setting";
        }
        projectService.update(project);
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{id}/addresses/add", method = RequestMethod.POST)
    public String addMailAddressToProject(@PathVariable("id") int id, @ModelAttribute MailAddress mailAddress, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mailAddress", mailAddress);
        if (mailAddress.getId() == null) {
            result.rejectValue("address", "address.isEmpty", "Address cannot be empty.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mailAddress", result);
            return "redirect:/projects/{id}/setting";
        }
        projectFormValidator.validate(mailAddress, projectService.findById(id), result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mailAddress", result);
            return "redirect:/projects/{id}/setting";
        }
        projectService.addMailAddressToProject(mailAddress.getId(), id);
        redirectAttributes.addFlashAttribute("mailAddress", new MailAddress());
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{pid}/addresses/{mid}/delete", method = RequestMethod.GET)
    public String deleteMailAddress(@PathVariable("pid") int pid, @PathVariable("mid") int mid, Model model) {
        projectService.deleteMailAddress(pid, mid);
        return "redirect:/projects/{pid}/setting";
    }

    @RequestMapping(value = "/projects/{id}/alertwordrule/add", method = RequestMethod.POST)
    public String addAlertWordRule(@PathVariable("id") int id, @Validated @ModelAttribute AlertWordRule alertWordRule, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("newAlertWordRule", alertWordRule);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newAlertWordRule", result);
            return "redirect:/projects/{id}/setting";
        }
        alertWordRuleService.add(id, alertWordRule.getThreshold());
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{id}/alertwordrule", params = "update", method = RequestMethod.POST)
    public String updateAlertWordRule(@Validated @ModelAttribute AlertWordRule alertWordRule, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("alertWordRule", alertWordRule);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.alertWordRule", result);
            return "redirect:/projects/{id}/setting";
        }
        alertWordRuleService.update(alertWordRule);
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{id}/alertwordrule", params = "delete", method = RequestMethod.POST)
    public String deleteAlertWordRule(@PathVariable("id") int id, Model model) {
        alertWordRuleService.delete(id);
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{id}/mailcountrule/add", method = RequestMethod.POST)
    public String addMailCountRule(@PathVariable("id") int id, @Validated @ModelAttribute MailCountRule mailCountRule, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("newMailCountRule", mailCountRule);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newMailCountRule", result);
            return "redirect:/projects/{id}/setting";
        }
        mailCountRuleService.add(id, mailCountRule.getThreshold());
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{id}/mailcountrule", params = "update", method = RequestMethod.POST)
    public String updateMailCountRule(@Validated @ModelAttribute MailCountRule mailCountRule, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mailCountRule", mailCountRule);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mailCountRule", result);
            return "redirect:/projects/{id}/setting";
        }
        mailCountRuleService.update(mailCountRule);
        return "redirect:/projects/{id}/setting";
    }

    @RequestMapping(value = "/projects/{id}/mailcountrule", params = "delete", method = RequestMethod.POST)
    public String deleteMailCountRule(@PathVariable("id") int id, Model model) {
        mailCountRuleService.delete(id);
        return "redirect:/projects/{id}/setting";
    }
}
