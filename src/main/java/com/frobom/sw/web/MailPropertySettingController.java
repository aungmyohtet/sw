package com.frobom.sw.web;

import java.util.List;
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

    @RequestMapping(value = "/mailpropertysettings", method = RequestMethod.GET)
    public String showMailPropertySettings(Model model) {
        model.addAttribute("mailPropertySetting", new MailPropertySetting());
        model.addAttribute("mailPropertySettings", this.mailPropertySettingService.findAll());
        model.addAttribute("mailAddressLists", this.mailAdddressService.findAll());
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());

        return "mail_property_settings";
    }

    @RequestMapping(value = "/mailpropertysettings/add", method = RequestMethod.POST)
    public String addMailPropertySetting(@Validated @ModelAttribute MailPropertySetting mailPropertySetting, BindingResult result, Model model) {
        model.addAttribute("mailAddressLists", this.mailAdddressService.findAll());
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());
        model.addAttribute("mailPropertySettings", this.mailPropertySettingService.findAll());

        if (result.hasErrors()) {
            return "mail_property_settings";
        }

        mailPropertySettingValidator.validate(mailPropertySetting, result);
        if (result.hasErrors()) {
            return "mail_property_settings";
        }

        mailPropertySettingService.add(mailPropertySetting);
        return "redirect:/mailpropertysettings";
    }

    @RequestMapping(value = "/mailpropertysettings/update/{id}/{mailAddressId}", method = RequestMethod.GET)
    public String updateMailPropertySetting(@PathVariable("id") int id, @PathVariable("mailAddressId") int mailAddressId,
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
        return "update_mail_property_setting";
    }

    @RequestMapping(value = "/mailpropertysettings/update", method = RequestMethod.POST)
    public String updateMailPropertySetting(@Validated @ModelAttribute MailPropertySetting mailPropertySetting, BindingResult result, Model model) {

        model.addAttribute("mailPropertySetting", mailPropertySetting);
        mailPropertySettingService.update(mailPropertySetting);
        return "redirect:/mailpropertysettings";
    }

    @RequestMapping(value = "/mailpropertysettings/delete/{id}/{mailAddressId}", method = RequestMethod.GET)
    public String deleteMailPropertySetting(@PathVariable("id") int id, @PathVariable("mailAddressId") int mailAddressId, Model model) {
        mailPropertySettingService.delete(id, mailAddressId);
        return "redirect:/mailpropertysettings";
    }

}
