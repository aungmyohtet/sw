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

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.validator.MailAddressFormValidator;

@Controller
public class MailAddressController {

    @Autowired
    @Qualifier("mailAddressFormValidator")
    private MailAddressFormValidator mailAddressFormValidator;

    @Autowired
    private MailAddressService mailAddressService;

    public void setMailAddressService(MailAddressService mailAddressService) {
        this.mailAddressService = mailAddressService;
    }

    @RequestMapping(value = "/mailAddresses/add", method = RequestMethod.GET)
    public String addMailAddressForm(Model model) {
        model.addAttribute("mailAddress", new MailAddress());
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        return "addMailAddress";
    }

    @RequestMapping(value = "/mailAddresses/add", method = RequestMethod.POST)
    public String saveMailAddress(@Validated @ModelAttribute MailAddress mailAddress, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("mailAddresses", mailAddressService.findAll());
            return "addMailAddress";
        }
        mailAddressFormValidator.validate(mailAddress, result);
        if (result.hasErrors()) {
            model.addAttribute("mailAddresses", mailAddressService.findAll());
            return "addMailAddress";
        }
        mailAddressService.save(mailAddress);
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        return "redirect:/mailAddresses/add";
    }

    @RequestMapping(value = "/mailAddresses/{id}", method = RequestMethod.GET)
    public String showDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("mailAddress", mailAddressService.findById(id));
        return "mailAddressDetails";
    }
}
