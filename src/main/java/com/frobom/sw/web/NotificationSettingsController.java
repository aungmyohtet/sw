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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frobom.sw.entity.AlertWord;
import com.frobom.sw.entity.AlertWordCountListener;
import com.frobom.sw.entity.AlertWordCountSetting;
import com.frobom.sw.entity.MailCountListener;
import com.frobom.sw.entity.MailCountSetting;
import com.frobom.sw.entity.AlertWord.Language;
import com.frobom.sw.service.AlertWordCountListenerService;
import com.frobom.sw.service.AlertWordCountSettingService;
import com.frobom.sw.service.AlertWordService;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.MailCountListenerService;
import com.frobom.sw.service.MailCountSettingService;
import com.frobom.sw.validator.AlertWordCountListenerFormValidator;
import com.frobom.sw.validator.AlertWordCountSettingFormValidator;
import com.frobom.sw.validator.AlertWordFormValidator;
import com.frobom.sw.validator.MailCountListenerValidator;
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

    @Autowired
    private AlertWordCountListenerService alertWordCountListenerService;

    public void setAlertWordCountListenerService(AlertWordCountListenerService alertWordCountListenerService) {
        this.alertWordCountListenerService = alertWordCountListenerService;
    }

    @Autowired
    private MailCountListenerService mailCountListenerService;

    public void setMailCountListenerService(MailCountListenerService mailCountListenerService) {
        this.mailCountListenerService = mailCountListenerService;
    }

    @Autowired
    @Qualifier("alertWordCountListenerFormValidator")
    AlertWordCountListenerFormValidator alertWordListenerValidator;

    @Autowired
    @Qualifier("mailCountListenerValidator")
    MailCountListenerValidator mailCountListenerValidator;

    @Autowired
    private MailAddressService mailAddressService;

    public void setMailAddressService(MailAddressService mailAddressService) {
        this.mailAddressService = mailAddressService;
    }

    @Autowired
    @Qualifier("alertWordFormValidator")
    private AlertWordFormValidator alertWordValidator;

    @Autowired
    private AlertWordService alertWordService;

    public void setAlertWordService(AlertWordService alertWordService) {
        this.alertWordService = alertWordService;
    }

    String oldAlertWordCountSettingName = "";

    String oldAlertWordCountValue = "";

    String oldMailCountSettingName = "";

    String oldMailCountValue = "";

    String alertWord;

    Language language;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String showSettings(Model model) {
        if (!model.containsAttribute("alertWordCountSetting")) {
            model.addAttribute("alertWordCountSetting", new AlertWordCountSetting());
        }

        if (!model.containsAttribute("mailCountSetting")) {
            model.addAttribute("mailCountSetting", new MailCountSetting());
        }
        if (!model.containsAttribute("alertWordCountListener")) {
            model.addAttribute("alertWordCountListener", new AlertWordCountListener());
        }

        if (!model.containsAttribute("mailCountListener")) {
            model.addAttribute("mailCountListener", new MailCountListener());
        }

        if (!model.containsAttribute("alertWord")) {
            model.addAttribute("alertWord", new AlertWord());
        }
        model.addAttribute("alertWordCountSettingNames", alertWordCountSettingService.getAlertWordCountSettingNames());
        model.addAttribute("alertWordCountSettings", alertWordCountSettingService.findAll());
        model.addAttribute("mailCountSettingNames", mailCountSettingService.getMailCountSettingNames());
        model.addAttribute("mailcountsettings", mailCountSettingService.findAll());
        model.addAttribute("alertWordCountListeners", alertWordCountListenerService.findAll());
        model.addAttribute("mailCountListeners", this.mailCountListenerService.findAll());
        model.addAttribute("mailaddresses", mailAddressService.findAll());
        model.addAttribute("alertWords", alertWordService.findAll());
        return "settings";
    }

    // AlertWordCountSetting
    @RequestMapping(value = "/settings/alert_word_count/add", method = RequestMethod.POST)
    public String addAlertWordCount(@Validated @ModelAttribute("alertWordCountSetting") AlertWordCountSetting alertWordCountSetting, BindingResult result, Model model,
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

    //AlertWordCountListener
    @RequestMapping(value = "/settings/alert_word_count_listeners/add", method = RequestMethod.POST)
    public String addAlertWordCountListener(@ModelAttribute AlertWordCountListener alertWordCountListener, BindingResult result, Model model, RedirectAttributes attr) {

        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.alertWordCountListener", result);
            attr.addFlashAttribute("alertWordCountListener", alertWordCountListener);
            return "redirect:/settings";
        }

        alertWordListenerValidator.validate(alertWordCountListener, result);
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.alertWordCountListener", result);
            attr.addFlashAttribute("alertWordCountListener", alertWordCountListener);
            return "redirect:/settings";
        }

        alertWordCountListenerService.add(alertWordCountListener.getMailAddress().getId());
        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/alert_word_count_listeners/{id}/delete", method = RequestMethod.GET)
    public String deleteAlertWordCountListener(@PathVariable("id") int id, Model model) {
        AlertWordCountListener alertWordCountListener = alertWordCountListenerService.findById(id);
        if(alertWordCountListener == null) {
            return "redirect:/invalid/request";
        }
        alertWordCountListenerService.remove(alertWordCountListener.getMailAddress().getId());
        return "redirect:/settings";
    }

    @RequestMapping(value = "/invalid/request", method = RequestMethod.GET)
    @ResponseBody
    public String invalidRequestPage(Model model) {
        return "Bad Request!";
    }

    //MailCountListener
    @RequestMapping(value = "/settings/mail_count_listeners/add", method = RequestMethod.POST)
    public String addMailCountListener(@Validated @ModelAttribute MailCountListener mailCountListener, BindingResult result, Model model, RedirectAttributes attr) {

        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.mailCountListener", result);
            attr.addFlashAttribute("mailCountListener", mailCountListener);
            return "redirect:/settings";
        }

        mailCountListenerValidator.validate(mailCountListener, result);
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.mailCountListener", result);
            attr.addFlashAttribute("mailCountListener", mailCountListener);
            return "redirect:/settings";
        }

        mailCountListenerService.add(mailCountListener);
        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/mail_count_listeners/{id}/delete", method = RequestMethod.GET)
    public String deleteMailCountListener(@PathVariable("id") int id, Model model) {
        mailCountListenerService.delete(id);
        return "redirect:/settings";
    }

    //AlertWord
    @RequestMapping(value = "/settings/alert_words/add", method = RequestMethod.POST)
    public String saveAlertWord(@Validated @ModelAttribute AlertWord alertWord, BindingResult result, Model model, RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.alertWord", result);
            attr.addFlashAttribute("alertWord", alertWord);
            return "redirect:/settings";
        }
        alertWordValidator.validate(alertWord, result);
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.alertWord", result);
            attr.addFlashAttribute("alertWord", alertWord);
            return "redirect:/settings";
        }
        alertWordService.save(alertWord);
        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/alert_words/{id}/update", method = RequestMethod.GET)
    public String showEditForm(@PathVariable("id") int id, Model model) {
        alertWord = alertWordService.findById(id).getWord();
        language = alertWordService.findById(id).getLanguage();
        if (!model.containsAttribute("updateAlertWord")) {
            model.addAttribute("updateAlertWord", alertWordService.findById(id));
        }
        return "edit_alert_word";
    }

    @RequestMapping(value = "/settings/alert_words/{id}/update", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute AlertWord updateAlertWord, BindingResult result, Model model,  RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.updateAlertWord", result);
            attr.addFlashAttribute("updateAlertWord", updateAlertWord);
            return "redirect:/settings/alert_words/{id}/update";
        }

        if (this.alertWord.equals(updateAlertWord.getWord()) && this.language.equals(updateAlertWord.getLanguage())) {
            return "redirect:/settings";
        }

        alertWordValidator.validate(updateAlertWord, result);
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.updateAlertWord", result);
            attr.addFlashAttribute("updateAlertWord", updateAlertWord);
            return "redirect:/settings/alert_words/{id}/update";
        }

        alertWordService.update(updateAlertWord);
        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/alert_words/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, Model model) {
        alertWordService.delete(id);
        return "redirect:/settings";
    }
}
