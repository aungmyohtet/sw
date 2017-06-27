package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.AlertWordCountListener;
import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.repository.AlertWordCountListenerRepository;
import com.frobom.sw.repository.MailAddressRepository;

@Service("alertWordCountListenerService")
public class AlertWordCountListenerServiceImpl implements AlertWordCountListenerService{

    @Autowired
    private AlertWordCountListenerRepository alertWordCountListenerRepository;

    @Autowired
    private MailAddressRepository mailAddressRepository;

    public void setAlertWordCountListenerRepository(AlertWordCountListenerRepository alertWordCountListenerRepository) {
        this.alertWordCountListenerRepository = alertWordCountListenerRepository;
    }

    public void setMailAddressRepository(MailAddressRepository mailAddressRepository) {
        this.mailAddressRepository = mailAddressRepository;
    }


    @Override
    @Transactional
    public void add(AlertWordCountListener alertWordCountListener) {
        alertWordCountListenerRepository.add(alertWordCountListener);
    }

    @Override
    @Transactional
    public List<AlertWordCountListener> findAll() {
        return alertWordCountListenerRepository.findAll();
    }

    @Override
    @Transactional
    public AlertWordCountListener findById(int id) {
        return alertWordCountListenerRepository.findById(id);
    }

    @Override
    @Transactional
    public void add(int mailAddressId) {
        MailAddress foundMailAddress = null;
        foundMailAddress = mailAddressRepository.findById(mailAddressId);
        System.out.println("id : " + foundMailAddress.getId());
        if (foundMailAddress != null) {
            AlertWordCountListener alertWordCountListener = new AlertWordCountListener();
            alertWordCountListener.setMailAddress(foundMailAddress);
            alertWordCountListenerRepository.add(alertWordCountListener);
        }
    }

    @Override
    @Transactional
    public AlertWordCountListener findByMailAddress(MailAddress mailAddress) {
        return alertWordCountListenerRepository.findByMailAddress(mailAddress);
    }

    @Override
    @Transactional
    public void remove(AlertWordCountListener alertWordCountListener) {
        alertWordCountListenerRepository.delete(alertWordCountListener);
    }

}
