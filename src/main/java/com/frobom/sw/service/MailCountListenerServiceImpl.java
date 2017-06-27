package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.MailCountListener;
import com.frobom.sw.repository.MailCountListenerRepository;

@Service("mailCountListenerService")
public class MailCountListenerServiceImpl implements MailCountListenerService {

    @Autowired
    private MailAddressService mailAdddressService;

    public void setMailAddressService(MailAddressService mailAdddressService) {
        this.mailAdddressService = mailAdddressService;
    }

    @Autowired
    private MailCountListenerRepository mailCountListenerRepository;

    public void setMailCountListenerRepository(MailCountListenerRepository mailCountListenerRepository) {
        this.mailCountListenerRepository = mailCountListenerRepository;
    }

    @Override
    @Transactional
    public void add(MailCountListener mailCountListener) {
        MailAddress mailAddress = this.mailAdddressService.findByAddress(mailCountListener.getMailAddress().getAddress());
        mailCountListener.setMailAddress(mailAddress);
        mailCountListenerRepository.save(mailCountListener);
    }

    @Override
    public List<MailCountListener> findAll() {

        return mailCountListenerRepository.findAll();
    }

}