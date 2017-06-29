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

import com.frobom.sw.entity.MailCountRule;
import com.frobom.sw.service.MailCountRuleService;
import com.frobom.sw.service.ProjectService;
import com.frobom.sw.validator.MailCountRuleValidator;

@Controller
public class MailCountRuleController {

    @Autowired
    @Qualifier("mailCountRuleValidator")
    private MailCountRuleValidator mailCountRuleValidator;

    @Autowired
    private MailCountRuleService mailCountRuleService;

    public void setMailCountRuleService(MailCountRuleService mailCountRuleService) {
        this.mailCountRuleService = mailCountRuleService;
    }

    @Autowired
    private ProjectService projectService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/mailcountrule", method = RequestMethod.GET)
    public String showMailCountRule(Model model) {
        model.addAttribute("mailCountRule", new MailCountRule());
        model.addAttribute("projectLists", this.projectService.findAll());
        model.addAttribute("mailCountRuleLists", this.mailCountRuleService.findAll());
        return "mailcountrule";
    }

    @RequestMapping(value = "/mailcountrule/add", method = RequestMethod.POST)
    public String AddMailCountRule(@Validated @ModelAttribute MailCountRule mailCountRule, BindingResult result, Model model) {
        model.addAttribute("projectLists", this.projectService.findAll());
        model.addAttribute("mailCountRuleLists", this.mailCountRuleService.findAll());

        if (result.hasErrors()) {
            return "mailcountrule";
        }

        mailCountRuleValidator.validate(mailCountRule, result);
        if (result.hasErrors()) {
            return "mailcountrule";
        }

        mailCountRuleService.add(mailCountRule);
        return "redirect:/mailcountrule";
    }

    @RequestMapping(value="/mailcountrule/update/{id}", method=RequestMethod.GET)
    public String showUpdateForm(@PathVariable int id, Model model) {
        MailCountRule mailCountRule = mailCountRuleService.findById(id);
        if(mailCountRule == null) {
            return "redirect:/bad/request";
        }
        model.addAttribute("mailCountRule", mailCountRule);
        return "update_mailcountrule";
     }

    @RequestMapping(value = "/mailcountrule/update", method = RequestMethod.POST)
    public String updateAlertWordRule(@Validated @ModelAttribute MailCountRule mailCountRule, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "update_mailcountrule";
        }
        mailCountRuleService.update(mailCountRule);
        return "redirect:/mailcountrule";
    }

    @RequestMapping(value = "/mailcountrule/remove/{id}", method = RequestMethod.GET)
    public String deleteAlertWordRule(@PathVariable int id, Model model) {
        MailCountRule mailCountRule = mailCountRuleService.findById(id);
        if (mailCountRule == null) {
            return "redirect:/bad/request";
        }
        mailCountRuleService.delete(id);
        return "redirect:/mailcountrule";
    }
}
