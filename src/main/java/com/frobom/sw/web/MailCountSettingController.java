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

import com.frobom.sw.entity.MailCountSetting;
import com.frobom.sw.service.MailCountSettingService;
import com.frobom.sw.validator.MailCountSettingFormValidator;

@Controller
public class MailCountSettingController {

    @Autowired
    private MailCountSettingService mailCountSettingService;

    @Autowired
    @Qualifier("mailCountSettingFormValidator")
    private MailCountSettingFormValidator mailCountSettingFormValidator;

    String oldSettingName = "";

    public void setmailCountSettingService(MailCountSettingService mailCountSettingService) {
        this.mailCountSettingService = mailCountSettingService;
    }

    @RequestMapping(value = "/mailcountsettings/add", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("mailCountSetting", new MailCountSetting());
        model.addAttribute("mailcountsettings", mailCountSettingService.findAll());
        return "add_mailcountsetting";
    }

    @RequestMapping(value = "/mailcountsettings/add", method = RequestMethod.POST)
    public String addMailCountSetting(@Validated @ModelAttribute MailCountSetting mailCountSetting, BindingResult result, Model model) {
        model.addAttribute("mailcountsettings", mailCountSettingService.findAll());

        if (result.hasErrors()) {
            return "add_mailcountsetting";
        }

        mailCountSettingFormValidator.validate(mailCountSetting, result);
        if (result.hasErrors()) {
            return "add_mailcountsetting";
        }

        mailCountSettingService.save(mailCountSetting);
        return "redirect:/mailcountsettings/add";
    }

    @RequestMapping(value="/mailcountsettings/remove/{id}", method=RequestMethod.GET)
    public String deleteMailCountSetting(@PathVariable int id, Model model) {
        MailCountSetting mailCountSetting = mailCountSettingService.findById(id);
        if(mailCountSetting == null) {
            return "redirect:/bad/request";
        }
        mailCountSettingService.delete(mailCountSetting.getId());
        return "redirect:/mailcountsettings/add";
     }

    @RequestMapping(value="/mailcountsettings/update/{id}", method=RequestMethod.GET)
    public String showUpdateForm(@PathVariable int id, Model model) {
        MailCountSetting mailCountSetting = mailCountSettingService.findById(id);
        if(mailCountSetting == null) {
            return "redirect:/bad/request";
        }
        model.addAttribute("mailCountSetting", mailCountSetting);
        oldSettingName = mailCountSetting.getName();
        return "update_mailcountsetting";
     }

    @RequestMapping(value = "/mailcountsettings/update", method = RequestMethod.POST)
    public String updateMailCountSetting(@Validated @ModelAttribute MailCountSetting mailCountSetting, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "update_mailcountsetting";
        }

        if (oldSettingName.equals(mailCountSetting.getName())) {
            return "redirect:/mailcountsettings/add";
        }

        mailCountSettingFormValidator.validate(mailCountSetting, result);
        if (result.hasErrors()) {
            return "update_mailcountsetting";
        }

        mailCountSettingService.update(mailCountSetting);
        return "redirect:/mailcountsettings/add";
    }
}
