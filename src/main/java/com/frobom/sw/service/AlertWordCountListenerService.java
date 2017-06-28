package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.AlertWordCountListener;
import com.frobom.sw.entity.MailAddress;
public interface AlertWordCountListenerService {

    void add(AlertWordCountListener alertWordCountListener);

    List<AlertWordCountListener> findAll();

    AlertWordCountListener findById(int id);

    void add(int maillAddressId);

    AlertWordCountListener findByMailAddress(MailAddress mailAddress);

    void remove(int id);
}
