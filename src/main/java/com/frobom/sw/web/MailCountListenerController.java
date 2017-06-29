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

import com.frobom.sw.entity.MailCountListener;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.MailCountListenerService;
import com.frobom.sw.validator.MailCountListenerValidator;

@Controller
public class MailCountListenerController {

    @Autowired
    @Qualifier("mailCountListenerValidator")
    private MailCountListenerValidator mailCountListenerValidator;

    @Autowired
    private MailAddressService mailAdddressService;

    public void setMailAddressService(MailAddressService mailAdddressService) {
        this.mailAdddressService = mailAdddressService;
    }

    @Autowired
    private MailCountListenerService mailCountListenerService;

    public void setMailCountListenerService(MailCountListenerService mailCountListenerService) {
        this.mailCountListenerService = mailCountListenerService;
    }

    @RequestMapping(value = "/mailcountlistener", method = RequestMethod.GET)
    public String showMailCountAddress(Model model) {
        model.addAttribute("mailCountListener", new MailCountListener());
        model.addAttribute("mailAddressLists", this.mailAdddressService.findAll());
        model.addAttribute("mailCountListeners", this.mailCountListenerService.findAll());
        return "mailcountlistener";
    }

    @RequestMapping(value = "/mailcountlistener/add", method = RequestMethod.POST)
    public String addailCountAddress(@Validated @ModelAttribute MailCountListener mailCountListener, BindingResult result, Model model) {
        model.addAttribute("mailCountListeners", this.mailCountListenerService.findAll());
        model.addAttribute("mailAddressLists", this.mailAdddressService.findAll());

        if (result.hasErrors()) {
            return "mailcountlistener";
        }

        mailCountListenerValidator.validate(mailCountListener, result);
        if (result.hasErrors()) {
            return "mailcountlistener";
        }

        mailCountListenerService.add(mailCountListener);
        return "redirect:/mailcountlistener";
    }

    @RequestMapping(value = "/mailcountlistener/delete/{id}", method = RequestMethod.GET)
    public String deleteMailCountAddress(@PathVariable("id") int id, Model model) {
        mailCountListenerService.delete(id);
        return "redirect:/mailcountlistener";
    }
}
