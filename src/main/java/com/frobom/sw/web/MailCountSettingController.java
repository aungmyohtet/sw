package com.frobom.sw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
