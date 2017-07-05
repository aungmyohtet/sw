package com.frobom.sw.web;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.frobom.sw.entity.AlertWordCountSetting;
import com.frobom.sw.entity.MailCountSetting;
import com.frobom.sw.service.AlertWordCountSettingService;
import com.frobom.sw.service.MailCountSettingService;
import com.frobom.sw.validator.AlertWordCountSettingFormValidator;
import com.frobom.sw.validator.MailCountSettingFormValidator;

@Controller
public class NotificationSettingsController {

    @Autowired
    private AlertWordCountSettingService alertWordCountSettingService;

    @Autowired
    @Qualifier("alertWordCountSettingFormValidator")
    private AlertWordCountSettingFormValidator validator;

    public void setAlertWordCountSettingService(AlertWordCountSettingService alertWordCountSettingService) {
        this.alertWordCountSettingService = alertWordCountSettingService;
    }

    @Autowired
    private MailCountSettingService mailCountSettingService;

    @Autowired
    @Qualifier("mailCountSettingFormValidator")
    private MailCountSettingFormValidator mailCountSettingFormValidator;

    public void setmailCountSettingService(MailCountSettingService mailCountSettingService) {
        this.mailCountSettingService = mailCountSettingService;
    }

    String oldAlertWordCountSettingName = "";

    String oldAlertWordCountValue = "";

    String oldMailCountSettingName = "";

    String oldMailCountValue = "";

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String showSettings(Model model) {
        if (!model.containsAttribute("alertWordCountSetting")) {
            model.addAttribute("alertWordCountSetting", new AlertWordCountSetting());
        }
        model.addAttribute("alertWordCountSettingNames", alertWordCountSettingService.getAlertWordCountSettingNames());
        model.addAttribute("alertWordCountSettings", alertWordCountSettingService.findAll());
        if (!model.containsAttribute("mailCountSetting")) {
            model.addAttribute("mailCountSetting", new MailCountSetting());
        }
        model.addAttribute("mailCountSettingNames", mailCountSettingService.getMailCountSettingNames());
        model.addAttribute("mailcountsettings", mailCountSettingService.findAll());
        return "settings";
    }

    // AlertWordCountSetting
    @RequestMapping(value = "/settings/alert_word_count/add", method = RequestMethod.POST)
    public String addAlertWordCountListener(@Validated @ModelAttribute("alertWordCountSetting") AlertWordCountSetting alertWordCountSetting, BindingResult result, Model model,
            RedirectAttributes attr, HttpSession session) {
        model.addAttribute("alertwordcountsettings", alertWordCountSettingService.findAll());
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.alertWordCountSetting", result);
            attr.addFlashAttribute("alertWordCountSetting", alertWordCountSetting);
            return "redirect:/settings";
        } else {
            validator.validate(alertWordCountSetting, result);
            if (result.hasErrors()) {
                attr.addFlashAttribute("org.springframework.validation.BindingResult.alertWordCountSetting", result);
                attr.addFlashAttribute("alertWordCountSetting", alertWordCountSetting);
                return "redirect:/settings";
            }
        }
        alertWordCountSettingService.add(alertWordCountSetting);
        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/alert_word_count/{id}/delete", method = RequestMethod.GET)
    public String deleteAlertWordCountSetting(@PathVariable int id, Model model) {
        AlertWordCountSetting alertWordCountSetting = alertWordCountSettingService.findById(id);
        if (alertWordCountSetting == null) {
            return "redirect:/bad/request";
        }
        alertWordCountSettingService.remove(alertWordCountSetting.getId());
        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/alert_word_count/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable int id, Model model) {
        if (!model.containsAttribute("alertWordCountSetting")) {
            model.addAttribute("alertWordCountSetting", this.alertWordCountSettingService.findById(id));
        }
        AlertWordCountSetting alertWordCountSetting = this.alertWordCountSettingService.findById(id);
        if (alertWordCountSetting == null) {
            return "redirect:/bad/request";
        }
        oldAlertWordCountSettingName = alertWordCountSetting.getName();
        oldAlertWordCountValue = alertWordCountSetting.getValue();
        return "update_alertwordcountsetting";
    }

    @RequestMapping(value = "/settings/alert_word_count/{id}/update", method = RequestMethod.POST)
    public String updateAlertWordCountSetting(@Validated @ModelAttribute("alertWordCountSetting") AlertWordCountSetting alertWordCountSetting, BindingResult result, Model model,
            RedirectAttributes attr, HttpSession session) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.alertWordCountSetting", result);
            attr.addFlashAttribute("alertWordCountSetting", alertWordCountSetting);
            return "redirect:/settings/alert_word_count/{id}/update";
        } else if (oldAlertWordCountSettingName.equals(alertWordCountSetting.getName())) {
            if (oldAlertWordCountValue.equals(alertWordCountSetting.getValue())) {
                return "redirect:/settings";
            }
        } else {
            validator.validate(alertWordCountSetting, result);
            if (result.hasErrors()) {
                attr.addFlashAttribute("org.springframework.validation.BindingResult.alertWordCountSetting", result);
                attr.addFlashAttribute("alertWordCountSetting", alertWordCountSetting);
                return "redirect:/settings/alert_word_count/{id}/update";
            }
        }
        alertWordCountSettingService.update(alertWordCountSetting);
        return "redirect:/settings";
    }

    // MailCountSetting
    @RequestMapping(value = "/settings/mail_count/add", method = RequestMethod.POST)
    public String addMailCountSetting(@Validated @ModelAttribute("mailCountSetting") MailCountSetting mailCountSetting, BindingResult result, Model model, RedirectAttributes attr,
            HttpSession session) {
        model.addAttribute("mailcountsettings", mailCountSettingService.findAll());
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.mailCountSetting", result);
            attr.addFlashAttribute("mailCountSetting", mailCountSetting);
            return "redirect:/settings";
        } else {
            mailCountSettingFormValidator.validate(mailCountSetting, result);
            if (result.hasErrors()) {
                attr.addFlashAttribute("org.springframework.validation.BindingResult.mailCountSetting", result);
                attr.addFlashAttribute("mailCountSetting", mailCountSetting);
                return "redirect:/settings";
            }
        }
        mailCountSettingService.save(mailCountSetting);
        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/mail_count/{id}/delete", method = RequestMethod.GET)
    public String deleteMailCountSetting(@PathVariable int id, Model model) {
        MailCountSetting mailCountSetting = mailCountSettingService.findById(id);
        if (mailCountSetting == null) {
            return "redirect:/bad/request";
        }
        mailCountSettingService.delete(mailCountSetting.getId());
        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/mail_count/{id}/update", method = RequestMethod.GET)
    public String showMailCountSettingUpdateForm(@PathVariable int id, Model model) {
        if (!model.containsAttribute("mailCountSetting")) {
            model.addAttribute("mailCountSetting", this.mailCountSettingService.findById(id));
        }
        MailCountSetting mailCountSetting = mailCountSettingService.findById(id);
        if (mailCountSetting == null) {
            return "redirect:/bad/request";
        }
        oldMailCountSettingName = mailCountSetting.getName();
        oldMailCountValue = mailCountSetting.getValue();
        return "update_mailcountsetting";
    }

    @RequestMapping(value = "/settings/mail_count/{id}/update", method = RequestMethod.POST)
    public String updateMailCountSetting(@Validated @ModelAttribute("mailCountSetting") MailCountSetting mailCountSetting, BindingResult result, Model model,
            RedirectAttributes attr, HttpSession session) {

        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.mailCountSetting", result);
            attr.addFlashAttribute("mailCountSetting", mailCountSetting);
            return "redirect:/settings/mail_count/{id}/update";
        } else if (oldMailCountSettingName.equals(mailCountSetting.getName())) {
            if (oldMailCountValue.equals(mailCountSetting.getValue())) {
                return "redirect:/settings";
            }
        } else {
            mailCountSettingFormValidator.validate(mailCountSetting, result);
            if (result.hasErrors()) {
                attr.addFlashAttribute("org.springframework.validation.BindingResult.mailCountSetting", result);
                attr.addFlashAttribute("mailCountSetting", mailCountSetting);
                return "redirect:/settings/mail_count/{id}/update";
            }
        }
        mailCountSettingService.update(mailCountSetting);
        return "redirect:/settings";
    }
}
