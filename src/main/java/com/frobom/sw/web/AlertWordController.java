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

import com.frobom.sw.entity.AlertWord;
import com.frobom.sw.entity.AlertWord.Language;
import com.frobom.sw.entity.Project;
import com.frobom.sw.service.AlertWordService;
import com.frobom.sw.validator.AlertWordFormValidator;

@Controller
public class AlertWordController {

    String alertWord;

    Language language;

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

    @RequestMapping(value = "/alertWords/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable("id") int id, Model model) {
        alertWord = alertWordService.findById(id).getWord();
        language = alertWordService.findById(id).getLanguage();
        model.addAttribute("alertWord", alertWordService.findById(id));
        return "edit_alertWord";
    }

    @RequestMapping(value = "/alertWords/edit/{id}", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute AlertWord alertWord, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit_alertWord";
        }

        if (this.alertWord.equals(alertWord.getWord()) && this.language.equals(alertWord.getLanguage())) {
            return "redirect:/alertWords/add";
        } else {
            validator.validate(alertWord, result);
            if (result.hasErrors()) {
                return "edit_alertWord";
            }
        }

        alertWordService.update(alertWord);
        return "redirect:/alertWords/add";
    }

    @RequestMapping(value = "/alertWords/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, Model model) {
        alertWordService.delete(id);
        return "redirect:/alertWords/add";
    }
}
