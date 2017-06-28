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

import com.frobom.sw.entity.AlertWordCountSetting;
import com.frobom.sw.service.AlertWordCountSettingService;
import com.frobom.sw.validator.AlertWordCountSettingFormValidator;

@Controller
public class AlertWordCountSettingController {

    @Autowired
    private AlertWordCountSettingService alertWordCountSettingService;

    @Autowired
    @Qualifier("alertWordCountSettingFormValidator")
    private AlertWordCountSettingFormValidator validator;

    public void setAlertWordCountSettingService(AlertWordCountSettingService alertWordCountSettingService) {
        this.alertWordCountSettingService = alertWordCountSettingService;
    }

    String oldSettingName = "";

    @RequestMapping(value = "/alertwordcountsettings/add", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("alertWordCountSetting", new AlertWordCountSetting());
        model.addAttribute("alertwordcountsettings", alertWordCountSettingService.findAll());
        return "add_alertwordcountsetting";
    }

    @RequestMapping(value = "/alertwordcountsettings/add", method = RequestMethod.POST)
    public String addAlertWordCountListener(@Validated @ModelAttribute AlertWordCountSetting alertWordCountSetting, BindingResult result, Model model) {
        model.addAttribute("alertwordcountsettings", alertWordCountSettingService.findAll());

        if (result.hasErrors()) {
            return "add_alertwordcountsetting";
        }

        validator.validate(alertWordCountSetting, result);
        if (result.hasErrors()) {
            return "add_alertwordcountsetting";
        }

        alertWordCountSettingService.add(alertWordCountSetting);
        return "redirect:/alertwordcountsettings/add";
    }

    @RequestMapping(value="/alertwordcountsettings/remove/{id}", method=RequestMethod.GET)
    public String deleteAlertWordCountSetting(@PathVariable int id, Model model) {
        AlertWordCountSetting alertWordCountSetting = alertWordCountSettingService.findById(id);
        if(alertWordCountSetting == null) {
            return "redirect:/bad/request";
        }
        alertWordCountSettingService.remove(alertWordCountSetting.getId());
        return "redirect:/alertwordcountsettings/add";
     }

    @RequestMapping(value="/alertwordcountsettings/update/{id}", method=RequestMethod.GET)
    public String showUpdateForm(@PathVariable int id, Model model) {
        AlertWordCountSetting alertWordCountSetting = alertWordCountSettingService.findById(id);
        if(alertWordCountSetting == null) {
            return "redirect:/bad/request";
        }
        model.addAttribute("alertWordCountSetting", alertWordCountSetting);
        oldSettingName = alertWordCountSetting.getName();
        return "update_alertwordcountsetting";
     }

    @RequestMapping(value = "/alertwordcountsettings/update", method = RequestMethod.POST)
    public String updateAlertWordCountSetting(@Validated @ModelAttribute AlertWordCountSetting alertWordCountSetting, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "update_alertwordcountsetting";
        }

        if (oldSettingName.equals(alertWordCountSetting.getName())) {
            return "redirect:/alertwordcountsettings/add";
        }

        validator.validate(alertWordCountSetting, result);
        if (result.hasErrors()) {
            return "update_alertwordcountsetting";
        }

        alertWordCountSettingService.update(alertWordCountSetting);
        return "redirect:/alertwordcountsettings/add";
    }

}
