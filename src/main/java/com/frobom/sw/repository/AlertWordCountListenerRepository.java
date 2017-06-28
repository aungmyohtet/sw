package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.AlertWordCountListener;
import com.frobom.sw.entity.MailAddress;
public interface AlertWordCountListenerRepository {

    void add(AlertWordCountListener alertWordCountListener);

    List<AlertWordCountListener> findAll();

    AlertWordCountListener findById(int id);

    AlertWordCountListener findByMailAddress(MailAddress mailAddress);

    void delete(AlertWordCountListener alertWordCountListener);
}
