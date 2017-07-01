package com.frobom.sw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.frobom.sw.service.AlertWordCountService;
import com.frobom.sw.service.MailCountService;
import com.frobom.sw.service.MailService;

@Controller
public class MailController {

    @Autowired
    @Qualifier("mailService")
    private MailService mailService;

    @Autowired
    @Qualifier("mailCountService")
    private MailCountService mailCountService;

    @Autowired
    @Qualifier("alertWordCountService")
    private AlertWordCountService alertWordCountService;

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public void setMailCountService(MailCountService mailCountService) {
        this.mailCountService = mailCountService;
    }

    public void setAlertWordCountService(AlertWordCountService alertWordCountService) {
        this.alertWordCountService = alertWordCountService;
    }

    @RequestMapping(value = "/mails", method = RequestMethod.GET)
    public String ShowMails(Model model) {
        model.addAttribute("mails", mailService.findAll());
        return "mails";
    }

    @RequestMapping(value = "/mailCounts", method = RequestMethod.GET)
    public String ShowMailCount(Model model) {
        model.addAttribute("mailCounts", mailCountService.findAll());
        return "mailCounts";
    }

    @RequestMapping(value = "/alertWordCounts", method = RequestMethod.GET)
    public String ShowAlertWordCount(Model model) {
        model.addAttribute("mails", mailService.findAllByFetchingSubEntities());
        return "alertWordCounts";
    }
}
