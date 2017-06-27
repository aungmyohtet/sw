package com.frobom.sw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
