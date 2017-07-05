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
public class ResultController {

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

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String ShowResult(Model model) {
        return "result";
    }

    @RequestMapping(value = "/result/mails", method = RequestMethod.GET)
    public String showMailsResult(Model model) {
        model.addAttribute("mails", mailService.findAll());
        return "mails";
    }

    @RequestMapping(value = "/result/mail_counts", method = RequestMethod.GET)
    public String ShowMailCount(Model model) {
        model.addAttribute("mailCounts", mailCountService.findAll());
        return "mail_counts";
    }

    @RequestMapping(value = "/result/alert_word_counts", method = RequestMethod.GET)
    public String ShowAlertWordCount(Model model) {
        model.addAttribute("mails", mailService.findAllByFetchingSubEntities());
        return "alert_word_counts";
    }
}
