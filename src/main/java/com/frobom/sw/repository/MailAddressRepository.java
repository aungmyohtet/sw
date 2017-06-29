package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.MailAddress;

public interface MailAddressRepository {

    void save(MailAddress mailAddress);

    List<MailAddress> findAll();

    MailAddress findById(int id);

    MailAddress findByAddress(String address);

    MailAddress findByName(String name);

    void update(MailAddress mailAddress);

    void delete(MailAddress mailAddress);
}
