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
import com.frobom.sw.entity.MailPropertyKey;
import com.frobom.sw.service.MailPropertyKeyService;
import com.frobom.sw.service.MailPropertySettingService;
import com.frobom.sw.validator.MailPropertyKeyValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MailPropertyKeyController {

    @Autowired
    @Qualifier("mailPropertyKeyValidator")
    private MailPropertyKeyValidator mailPropertyKeyValidator;

    @Autowired
    private MailPropertyKeyService mailPropertyKeyService;

    public void setMailPropertyKeyService(MailPropertyKeyService mailPropertyKeyService) {
        this.mailPropertyKeyService = mailPropertyKeyService;
    }

    @Autowired
    private MailPropertySettingService mailPropertySettingService;

    public void setMailPropertySettingService(MailPropertySettingService mailPropertySettingService) {
        this.mailPropertySettingService = mailPropertySettingService;
    }

    @RequestMapping(value = "/mailpropertykeys", method = RequestMethod.GET)
    public String showMailPropertyKeys(Model model) {
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());
        model.addAttribute("mailPropertyKey", new MailPropertyKey());
        return "mail_property_keys";
    }

    @RequestMapping(value = "/mailpropertykeys/add", method = RequestMethod.POST)
    public String addMailPropertyKey(@Validated @ModelAttribute MailPropertyKey mailPropertyKey, BindingResult result, Model model) {
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());
        if (result.hasErrors()) {
            return "mail_property_keys";
        }

        mailPropertyKeyValidator.validate(mailPropertyKey, result);
        if (result.hasErrors()) {
            return "mail_property_keys";
        }

        mailPropertyKeyService.add(mailPropertyKey);
        return "redirect:/mailpropertykeys";
    }

    @RequestMapping(value = "/mailpropertykeys/update/{id}", method = RequestMethod.GET)
    public String updateMailPropertyKey(@PathVariable("id") int id, Model model) {
        model.addAttribute("mailPropertyKey", this.mailPropertyKeyService.findById(id));
        return "update_mail_property_key";
    }

    @RequestMapping(value = "/mailpropertykeys/update", method = RequestMethod.POST)
    public String updateMailPropertyKey(@Validated @ModelAttribute MailPropertyKey mailPropertyKey, BindingResult result, Model model) {
        String name = mailPropertyKey.getName();
        int id = mailPropertyKey.getId();

        if (result.hasErrors()) {
            return "update_mail_property_key";
        }

        if (mailPropertyKeyService.findById(id).getName().equals(name)) {
            mailPropertyKeyService.update(mailPropertyKey);
        } else {
            mailPropertyKeyValidator.validate(mailPropertyKey, result);
            if (result.hasErrors()) {
                return "update_mail_property_key";
            }
            mailPropertyKeyService.update(mailPropertyKey);
        }
        return "redirect:/mailpropertykeys";
    }

    @RequestMapping(value = "/mailpropertykeys/delete/{id}", method = RequestMethod.GET)
    public String deleteMailPropertyKey(@PathVariable("id") int id, Model model) {
        mailPropertySettingService.delete(id);
        mailPropertyKeyService.delete(id);
        return "redirect:/mailpropertykeys";
    }
}
