package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.repository.MailAddressRepository;

@Service("mailAddressService")
public class MailAddressServiceImpl implements MailAddressService{

    @Autowired
    private MailAddressRepository mailAddressRepo;
    
    public void setMailAddressRepo(MailAddressRepository mailAddressRepo) {
        this.mailAddressRepo = mailAddressRepo;
    }

    @Override
    @Transactional
    public void save(MailAddress mailAddress) {
       mailAddressRepo.save(mailAddress);
    }

    @Override
    @Transactional
    public List<MailAddress> findAll() {
       return mailAddressRepo.findAll();
    }

    @Override
    @Transactional
    public MailAddress findById(int id) {
       return mailAddressRepo.findById(id);
    }

    @Override
    @Transactional
    public MailAddress findByAddress(String address) {
       return mailAddressRepo.findByAddress(address);
    }

    @Override
    public MailAddress findByName(String name) {
        return mailAddressRepo.findByName(name);
    }

}
