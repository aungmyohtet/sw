package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.MailCountListener;

public interface MailCountListenerRepository {

    void save(MailCountListener mailCountListener);

    List<MailCountListener> findAll();

    MailCountListener findById(int id);

    void delete(int id);
}
