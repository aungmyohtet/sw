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

import com.frobom.sw.entity.AlertWordCountSetting;
import com.frobom.sw.entity.AlertWordRule;
import com.frobom.sw.service.AlertWordRuleService;
import com.frobom.sw.service.ProjectService;
import com.frobom.sw.validator.AlertWordRuleFormValidator;

@Controller
public class AlertWordRuleController {

    @Autowired
    private AlertWordRuleService alertWordRuleService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    @Qualifier("alertWordRuleFormValidator")
    private AlertWordRuleFormValidator alertWordRuleFormValidator;

    public void setAlertWordRuleService(AlertWordRuleService alertWordRuleService) {
        this.alertWordRuleService = alertWordRuleService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/alertwordrules/add", method = RequestMethod.GET)
    public String showAlertWordRuleForm(Model model) {
        model.addAttribute("alertWordRule", new AlertWordRule());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("alertWordRuleList", alertWordRuleService.findAll());
        return "addAlertWordRule";
    }

    @RequestMapping(value = "/alertwordrules/add", method = RequestMethod.POST)
    public String addAlertWordRule(@Validated @ModelAttribute AlertWordRule alertWordRule, BindingResult result, Model model) {
        model.addAttribute("alertWordRuleList", alertWordRuleService.findAll());
        model.addAttribute("projects", projectService.findAll());

        if (result.hasErrors()) {
            return "addAlertWordRule";
        }

        alertWordRuleFormValidator.validate(alertWordRule, result);
        if (result.hasErrors()) {
            return "addAlertWordRule";
        }

        alertWordRuleService.add(alertWordRule.getProject().getId(), alertWordRule.getThreshold());
        return "redirect:/alertwordrules/add";
    }

    @RequestMapping(value = "/alertwordrules/remove/{id}", method = RequestMethod.GET)
    public String deleteAlertWordRule(@PathVariable int id, Model model) {
        AlertWordRule alertWordRule = alertWordRuleService.findById(id);
        if (alertWordRule == null) {
            return "redirect:/bad/request";
        }
        alertWordRuleService.delete(id);
        return "redirect:/alertwordrules/add";
    }

    @RequestMapping(value="/alertwordrules/update/{id}", method=RequestMethod.GET)
    public String showUpdateForm(@PathVariable int id, Model model) {
        AlertWordRule alertWordRule = alertWordRuleService.findById(id);
        if(alertWordRule == null) {
            return "redirect:/bad/request";
        }
        model.addAttribute("alertWordRule", alertWordRule);
        return "update_alertwordrule";
     }

    @RequestMapping(value = "/alertwordrules/update", method = RequestMethod.POST)
    public String updateAlertWordRule(@Validated @ModelAttribute AlertWordRule alertWordRule, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "update_alertwordrule";
        }

        alertWordRuleService.update(alertWordRule);
        return "redirect:/alertwordrules/add";
    }
}
