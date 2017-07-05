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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.MailPropertySetting;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.MailPropertyKeyService;
import com.frobom.sw.service.MailPropertySettingService;
import com.frobom.sw.validator.MailAddressFormValidator;
import com.frobom.sw.validator.MailPropertySettingValidator;

@Controller
public class MailAddressController {

    @Autowired
    @Qualifier("mailAddressFormValidator")
    private MailAddressFormValidator mailAddressFormValidator;

    @Autowired
    @Qualifier("mailPropertySettingValidator")
    private MailPropertySettingValidator mailPropertySettingValidator;

    @Autowired
    private MailAddressService mailAddressService;

    @Autowired
    private MailPropertyKeyService mailPropertyKeyService;

    @Autowired
    private MailPropertySettingService mailPropertySettingService;

    String oldMailAddress = "";

    String oldName = "";

    String update = "";

    public void setMailAddressService(MailAddressService mailAddressService) {
        this.mailAddressService = mailAddressService;
    }

    @RequestMapping(value = "/Invalid_Address", method = RequestMethod.GET)
    @ResponseBody
    public String invalidAddressPage(Model model) {
        return "Bad Request";
    }

    @RequestMapping(value = "/mail_addresses", method = RequestMethod.GET)
    public String showMailAddresses(Model model) {
        model.addAttribute("mailAddress", new MailAddress());
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        return "mail_addresses";
    }

    @RequestMapping(value = "/mail_addresses/add", method = RequestMethod.POST)
    public String saveMailAddress(@Validated @ModelAttribute MailAddress mailAddress, BindingResult result, Model model) {
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        if (result.hasErrors()) {
            return "mail_addresses";
        }
        mailAddressFormValidator.validate(mailAddress, result);
        if (result.hasErrors()) {
            return "mail_addresses";
        }
        mailAddressService.save(mailAddress);
        return "redirect:/mail_addresses";
    }

    @RequestMapping(value = "/mail_addresses/{id}", method = RequestMethod.GET)
    public String showDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("mailAddress", mailAddressService.findById(id));
        return "mailAddressDetails";
    }

    @RequestMapping(value = "/mail_addresses/{id}/setting", method = RequestMethod.GET)
    public String showSettingPage(@PathVariable("id") int id, @ModelAttribute String updated, BindingResult result, Model model) {
        MailAddress mailAddress = mailAddressService.findById(id);
        if (mailAddress == null) {
            return "redirect:/Invalid_Address";
        }

        if (!model.containsAttribute("mailAddress")) {
            model.addAttribute("mailAddress", mailAddress);
        }

        MailPropertySetting mailPropertySetting = new MailPropertySetting();
        mailPropertySetting.setMailAddress(mailAddress);
        model.addAttribute("mailPropertyKeys", mailPropertyKeyService.findAll());
        model.addAttribute("mailPropertySetting", mailPropertySetting);
        model.addAttribute("mailPropertySettings", mailAddress.getPropertySettings());
        model.addAttribute("updated", update);
        oldMailAddress = mailAddress.getAddress();
        oldName = mailAddress.getName();
        return "mail_addresses_setting";
    }

    @RequestMapping(value = "/mail_addresses/{id}/update", method = RequestMethod.POST)
    public String updateMailAddress(@Validated @ModelAttribute MailAddress mailAddress, BindingResult result, @ModelAttribute String updated, BindingResult result2, Model model,
            RedirectAttributes attr) {

        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.mailAddress", result);
            attr.addFlashAttribute("mailAddress", mailAddress);
            attr.addFlashAttribute("org.springframework.validation.BindingResult.updated", result2);
            update = "";
            attr.addFlashAttribute("updated", update);
            return "redirect:/mail_addresses/{id}/setting";
        }
        if (!oldMailAddress.equals(mailAddress.getAddress())) {
            mailAddressFormValidator.validate(mailAddress, result);
            if (result.hasErrors()) {
                attr.addFlashAttribute("org.springframework.validation.BindingResult.mailAddress", result);
                attr.addFlashAttribute("mailAddress", mailAddress);
                attr.addFlashAttribute("org.springframework.validation.BindingResult.updated", result2);
                update = "";
                attr.addFlashAttribute("updated", update);
                return "redirect:/mail_addresses/{id}/setting";
            }
        }
        mailAddressService.update(mailAddress);
        update = "Mail Address is updated..";
        model.addAttribute("updated", update);
        return "redirect:/mail_addresses/{id}/setting";
    }

    @RequestMapping(value = "/mail_addresses/{id}/delete", method = RequestMethod.GET)
    public String deleteMailAddress(@PathVariable("id") int id, Model model) {
        mailAddressService.delete(id);
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        return "redirect:/mail_addresses";
    }

    @RequestMapping(value = "/mail_addresses/{id}/mail_property_settings/add", method = RequestMethod.POST)
    public String addMailPropertySetting(@PathVariable("id") int id, @Validated @ModelAttribute MailPropertySetting mailPropertySetting, BindingResult result, Model model) {
        MailAddress mailAddress = mailAddressService.findById(id);
        model.addAttribute("mailPropertyKeys", this.mailPropertyKeyService.findAll());
        model.addAttribute("mailPropertySettings", mailAddress.getPropertySettings());
        model.addAttribute("mailAddress", mailAddress);
        mailPropertySetting.setMailAddress(mailAddress);

        if (result.hasErrors()) {
            return "mail_addresses_setting";
        }

        mailPropertySettingValidator.validate(mailPropertySetting, result);
        if (result.hasErrors()) {
            return "mail_addresses_setting";
        }

        mailPropertySettingService.add(mailPropertySetting);
        return "redirect:/mail_addresses/{id}/setting";
    }

    @RequestMapping(value = "/mail_addresses/{mailAddressId}/mail_property_settings/{id}/update", method = RequestMethod.GET)
    public String updateMailPropertySetting(@PathVariable("id") int id, @PathVariable("mailAddressId") int mailAddressId,
            @Validated @ModelAttribute MailPropertySetting mailPropertySetting, BindingResult result, Model model) {

        mailPropertySetting.setMailAddress(mailAddressService.findById(mailAddressId));
        mailPropertySetting.setMailPropertyKey(mailPropertyKeyService.findById(id));
        mailPropertySetting = mailPropertySettingService.findByIds(id, mailAddressId);
        model.addAttribute("mailPropertySetting", mailPropertySetting);
        model.addAttribute("mailAddressId", mailAddressId);
        return "update_mail_property_setting";
    }

    @RequestMapping(value = "/mail_addresses/{mailAddressId}/mail_property_settings/{id}/update", method = RequestMethod.POST)
    public String updateMailPropertySetting(@Validated @ModelAttribute MailPropertySetting mailPropertySetting, BindingResult result, Model model, RedirectAttributes attr) {

        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.mailPropertySetting", result);
            attr.addFlashAttribute("mailPropertySetting", mailPropertySetting);
            return "redirect:/mail_addresses/{mailAddressId}/setting";
        }
        mailPropertySettingService.update(mailPropertySetting);
        return "redirect:/mail_addresses/{mailAddressId}/setting";
    }

    @RequestMapping(value = "/mail_addresses/{mailAddressId}/mail_property_settings/{id}/delete", method = RequestMethod.GET)
    public String deleteMailPropertySetting(@PathVariable("id") int id, @PathVariable("mailAddressId") int mailAddressId, Model model) {

        mailPropertySettingService.delete(id, mailAddressId);
        return "redirect:/mail_addresses/{mailAddressId}/setting";
    }
}
