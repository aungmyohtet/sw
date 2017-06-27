package com.frobom.sw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frobom.sw.entity.AlertWordCountListener;
import com.frobom.sw.service.AlertWordCountListenerService;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.validator.AlertWordCountListenerFormValidator;

@Controller
public class AlertWordCountListenerController {

    @Autowired
    private AlertWordCountListenerService alertWordCountListenerService;

    @Autowired
    private MailAddressService mailAddressService;

    @Autowired
    @Qualifier("alertWordCountListenerFormValidator")
    private AlertWordCountListenerFormValidator validator;

    public void setAlertWordCountListenerService(AlertWordCountListenerService alertWordCountListenerService) {
        this.alertWordCountListenerService = alertWordCountListenerService;
    }

    public void setValidator(AlertWordCountListenerFormValidator validator) {
        this.validator = validator;
    }

    @RequestMapping(value = "/alertwordcountlisteners/add", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("alertWordCountListener", new AlertWordCountListener());
        model.addAttribute("mailaddresses", mailAddressService.findAll());
        model.addAttribute("alertwordcountlisteners", alertWordCountListenerService.findAll());
        return "add_alertwordcountlistener";
    }

    @RequestMapping(value = "/alertwordcountlisteners/add", method = RequestMethod.POST)
    public String addAlertWordCountListener(@ModelAttribute AlertWordCountListener alertWordCountListener, BindingResult result, Model model) {
        model.addAttribute("mailaddresses", mailAddressService.findAll());
        model.addAttribute("alertwordcountlisteners", alertWordCountListenerService.findAll());

        if (result.hasErrors()) {
            return "add_alertwordcountlistener";
        }

        validator.validate(alertWordCountListener, result);
        if (result.hasErrors()) {
            return "add_alertwordcountlistener";
        }

        alertWordCountListenerService.add(alertWordCountListener.getMailAddress().getId());

        return "redirect:/alertwordcountlisteners/add";
    }
    
    @RequestMapping(value="/alertwordcountlisteners/remove/{id}", method=RequestMethod.GET)
    public String deleteAlertWordCountListener(@PathVariable int id, Model model) {
        AlertWordCountListener alertWordCountListener = alertWordCountListenerService.findById(id);
        if(alertWordCountListener == null) {
            return "redirect:/bad/request";
        }
        alertWordCountListenerService.remove(alertWordCountListener);
        return "redirect:/alertwordcountlisteners/add";
     }

    @RequestMapping(value = "/bad/request", method = RequestMethod.GET)
    @ResponseBody
    public String accessDeniedPage(Model model) {
        return "Bad Request!";
    }
}
