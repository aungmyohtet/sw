package com.frobom.sw.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.frobom.sw.entity.MailPropertySetting;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.MailPropertyKeyService;
import com.frobom.sw.service.MailPropertySettingService;
import com.frobom.sw.validator.MailPropertySettingValidator;

@Controller
public class MailPropertySettingController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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
    public String showMailPropertySetting(Model model) {
        model.addAttribute("mailPropertySetting", new MailPropertySetting());
        model.addAttribute("mailPropertySettings", this.mailPropertySettingService.findAll());
        model.addAttribute("mailAddressLists", this.mailAdddressService.findAll());
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());

        return "mailpropertysetting";
    }

    @RequestMapping(value = "/mailpropertysetting/add", method = RequestMethod.POST)
    public String addMailPropertySetting(@Validated @ModelAttribute MailPropertySetting mailPropertySetting, BindingResult result, Model model) {
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

    @RequestMapping(value = "/mailpropertysetting/edit/{id}/{mailAddressId}", method = RequestMethod.GET)
    public String editMailPropertySetting(@PathVariable("id") int id, @PathVariable("mailAddressId") int mailAddressId,
            @Validated @ModelAttribute MailPropertySetting mailPropertySetting, BindingResult result, Model model) {

        List<MailPropertySetting> mailPropertySettingLists = mailPropertySettingService.findAll();
        for (MailPropertySetting m : mailPropertySettingLists) {
            if (m.getMailPropertyKey().getId().equals(id) && m.getMailAddress().getId().equals(mailAddressId)) {
                mailPropertySetting.setValue(m.getValue());
                mailPropertySetting.setMailAddress(this.mailAdddressService.findById(mailAddressId));
                mailPropertySetting.setMailPropertyKey(this.mailPropertyKeyService.findById(id));
            }
        }

        model.addAttribute("mailProertySetting", mailPropertySetting);
        return "mailpropertysettingedit";
    }

    @RequestMapping(value = "/mailpropertysetting/edit/save", method = RequestMethod.POST)
    public String updateMailPropertySetting(@Validated @ModelAttribute MailPropertySetting mailPropertySetting, BindingResult result, Model model) {

        model.addAttribute("mailPropertySetting", mailPropertySetting);
        mailPropertySettingService.update(mailPropertySetting);
        return "redirect:/mailpropertysetting";
    }

    @RequestMapping(value = "/mailpropertysetting/delete/{id}/{mailAddressId}", method = RequestMethod.GET)
    public String deleteMailPropertySetting(@PathVariable("id") int id, @PathVariable("mailAddressId") int mailAddressId, Model model) {
        mailPropertySettingService.delete(id, mailAddressId);
        return "redirect:/mailpropertysetting";
    }

}
