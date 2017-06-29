package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.MailCountListener;

public interface MailCountListenerService {

    void add(MailCountListener mailCountListener);

    public List<MailCountListener> findAll();

    public void delete(int id);
}
