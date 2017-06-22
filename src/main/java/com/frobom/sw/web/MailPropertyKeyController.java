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

    @RequestMapping(value = "/mailpropertykey", method = RequestMethod.GET)
    public String showMailPropertyKey(Model model) {
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());
        model.addAttribute("mailPropertyKey", new MailPropertyKey());
        return "mailpropertykey";
    }

    @RequestMapping(value = "/mailpropertykey/add", method = RequestMethod.POST)
    public String addMailProperty(@Validated @ModelAttribute MailPropertyKey mailPropertyKey, BindingResult result, Model model) {
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());

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

}
