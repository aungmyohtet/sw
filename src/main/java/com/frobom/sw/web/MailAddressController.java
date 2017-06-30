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
import com.frobom.sw.entity.MailPropertySetting;
import com.frobom.sw.entity.Project;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.MailPropertyKeyService;
import com.frobom.sw.service.MailPropertySettingService;
import com.frobom.sw.validator.MailAddressFormValidator;

@Controller
public class MailAddressController {

    @Autowired
    @Qualifier("mailAddressFormValidator")
    private MailAddressFormValidator mailAddressFormValidator;

    @Autowired
    private MailAddressService mailAddressService;

    @Autowired
    private MailPropertyKeyService mailPropertyKeyService;

    @Autowired
    private MailPropertySettingService mailPropertySettingService;

    public void setMailAddressService(MailAddressService mailAddressService) {
        this.mailAddressService = mailAddressService;
    }

    @RequestMapping(value = "/mail_addresses", method = RequestMethod.GET)
    public String showMailAddresses(Model model) {
        model.addAttribute("mailAddress", new MailAddress());
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        return "mail_addresses";
    }

    @RequestMapping(value = "/mail_addresses/add", method = RequestMethod.POST)
    public String saveMailAddress(@Validated @ModelAttribute MailAddress mailAddress, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("mailAddresses", mailAddressService.findAll());
            return "mail_addresses";
        }
        mailAddressFormValidator.validate(mailAddress, result);
        if (result.hasErrors()) {
            model.addAttribute("mailAddresses", mailAddressService.findAll());
            return "mail_addresses";
        }
        mailAddressService.save(mailAddress);
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        return "redirect:/mail_addresses/add";
    }

    @RequestMapping(value = "/mail_addresses/{id}", method = RequestMethod.GET)
    public String showDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("mailAddress", mailAddressService.findById(id));
        return "mailAddressDetails";
    }

    @RequestMapping(value = "/mail_addresses/{id}/setting", method = RequestMethod.GET)
    public String showEditForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("mailAddress", mailAddressService.findById(id));
        model.addAttribute("mailPropertyKeys", mailPropertyKeyService.findAll());
        model.addAttribute("mailPropertySetting", new MailPropertySetting());
        model.addAttribute("mailPropertySettings", this.mailPropertySettingService.findAll());
        return "setting_mail_address";
    }

    @RequestMapping(value="/mail_addresses/{id}/update", method=RequestMethod.POST)
    public String updateMailAddress(@Validated @ModelAttribute MailAddress mailAddress, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "setting_mail_address";
        }
        mailAddressService.update(mailAddress);
        return "redirect:/mail_addresses/{id}/setting";
    }
    @RequestMapping(value="/mail_addresses/{id}/delete", method=RequestMethod.GET)
    public String deleteMailAddress(@PathVariable("id") int id, Model model) {
        mailAddressService.delete(id);
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        return "redirect:/mail_addresses";
    }
}
