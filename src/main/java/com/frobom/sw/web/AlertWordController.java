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

import com.frobom.sw.entity.AlertWord;
import com.frobom.sw.service.AlertWordService;
import com.frobom.sw.validator.AlertWordFormValidator;

@Controller
public class AlertWordController {

    @Autowired
    @Qualifier("alertWordFormValidator")
    private AlertWordFormValidator validator;

    @Autowired
    private AlertWordService alertWordService;

    public void setAlertWordService(AlertWordService alertWordService) {
        this.alertWordService = alertWordService;
    }

    @RequestMapping(value = "/alertWords/add", method = RequestMethod.GET)
    public String addAlertWordForm(Model model) {
        model.addAttribute("alertWord", new AlertWord());
        model.addAttribute("alertWords", alertWordService.findAll());
        return "add_alertWord";
    }

    @RequestMapping(value = "/alertWords/add", method = RequestMethod.POST)
    public String saveAlertWord(@Validated @ModelAttribute AlertWord alertWord, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("alertWords", alertWordService.findAll());
            return "add_alertWord";
        }
        validator.validate(alertWord, result);
        if (result.hasErrors()) {
            model.addAttribute("alertWords", alertWordService.findAll());
            return "add_alertWord";
        }
        alertWordService.save(alertWord);
        return "redirect:/alertWords/add";
    }
}
