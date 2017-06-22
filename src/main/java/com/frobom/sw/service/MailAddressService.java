package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.MailAddress;

public interface MailAddressService {
    
    void save(MailAddress mailAddress);

    List<MailAddress> findAll();

    MailAddress findById(int id);

    MailAddress findByAddress(String address);

    MailAddress findByName(String name);

}
