package com.frobom.sw.web;

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

import com.frobom.sw.entity.MailPropertyKey;
import com.frobom.sw.service.MailPropertyKeyService;
import com.frobom.sw.service.MailPropertySettingService;
import com.frobom.sw.validator.MailPropertyKeyValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MailPropertyKeyController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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

    @RequestMapping(value = "/mailpropertykey", method = RequestMethod.GET)
    public String showMailPropertyKey(Model model) {
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());
        model.addAttribute("mailPropertyKey", new MailPropertyKey());
        return "mailpropertykey";
    }

    @RequestMapping(value = "/mailpropertykey/add", method = RequestMethod.POST)
    public String addMailProperty(@Validated @ModelAttribute MailPropertyKey mailPropertyKey, BindingResult result, Model model) {
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());
        if (result.hasErrors()) {
            return "mailpropertykey";
        }

        mailPropertyKeyValidator.validate(mailPropertyKey, result);
        if (result.hasErrors()) {
            return "mailpropertykey";
        }

        mailPropertyKeyService.add(mailPropertyKey);
        return "redirect:/mailpropertykey";
    }

    @RequestMapping(value = "mailpropertykey/{id}", method = RequestMethod.GET)
    public String showDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("mailPropertyKey", mailPropertyKeyService.findById(id));
        return "mailpropertykeydetails";

    }

    @RequestMapping(value = "/mailpropertykey/edit/{id}", method = RequestMethod.GET)
    public String editMailPropertyKey(@PathVariable("id") int id, Model model) {
        model.addAttribute("mailPropertyKey", this.mailPropertyKeyService.findById(id));
        return "mailpropertykeyedit";
    }

    @RequestMapping(value = "/mailpropertykey/edit/save", method = RequestMethod.POST)
    public String saveMailPropertyKey(@Validated @ModelAttribute MailPropertyKey mailPropertyKey, BindingResult result, Model model) {
        mailPropertyKeyValidator.validate(mailPropertyKey, result);
        if (result.hasErrors()) {
            return "mailpropertykeyedit";
        }
        mailPropertyKeyService.add(mailPropertyKey);
        return "redirect:/mailpropertykey";
    }

    @RequestMapping(value = "/mailpropertykey/delete/{id}", method = RequestMethod.GET)
    public String deleteMailPropertyKey(@PathVariable("id") int id, Model model) {
        mailPropertySettingService.delete(id);
        mailPropertyKeyService.delete(id);
        return "redirect:/mailpropertykey";
    }
}
