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

import com.frobom.sw.entity.MailPropertySetting;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.MailPropertyKeyService;
import com.frobom.sw.service.MailPropertySettingService;
import com.frobom.sw.validator.MailPropertySettingValidator;

@Controller
public class MailPropertySettingController {

    @Autowired
    private MailPropertyKeyService mailPropertyKeyService;

    public void setMailPropertyKeyService(MailPropertyKeyService mailPropertyKeyService) {
        this.mailPropertyKeyService = mailPropertyKeyService;
    }

    @Autowired
    private MailAddressService mailAdddressService;

    public void setMailAddressService(MailAddressService mailAdddressService) {
        this.mailAdddressService = mailAdddressService;
    }

    @Autowired
    private MailPropertySettingService mailPropertySettingService;

    public void setMailPropertySettingService(MailPropertySettingService mailPropertySettingService) {
        this.mailPropertySettingService = mailPropertySettingService;
    }

    @Autowired
    @Qualifier("mailPropertySettingValidator")
    private MailPropertySettingValidator mailPropertySettingValidator;

    @RequestMapping(value = "/mailpropertysetting", method = RequestMethod.GET)
    public String showMailPropertyKey(Model model) {
        model.addAttribute("mailPropertySetting", new MailPropertySetting());
        model.addAttribute("mailPropertySettings", this.mailPropertySettingService.findAll());
        model.addAttribute("mailAddressLists", this.mailAdddressService.findAll());
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());

        return "mailpropertysetting";
    }

    @RequestMapping(value = "/mailpropertysetting/add", method = RequestMethod.POST)
    public String addMailProperty(@Validated @ModelAttribute MailPropertySetting mailPropertySetting, BindingResult result, Model model) {
        model.addAttribute("mailAddressLists", this.mailAdddressService.findAll());
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());
        model.addAttribute("mailPropertySettings", this.mailPropertySettingService.findAll());

        if (result.hasErrors()) {
            return "mailpropertysetting";
        }

        mailPropertySettingValidator.validate(mailPropertySetting, result);
        if (result.hasErrors()) {
            return "mailpropertysetting";
        }

        mailPropertySettingService.add(mailPropertySetting);
        return "redirect:/mailpropertysetting";
    }
}
