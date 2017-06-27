package com.frobom.sw.service;

import java.util.List;
import com.frobom.sw.entity.MailCountRule;

public interface MailCountRuleService {

    void add(MailCountRule mailCountRule);

    public List<MailCountRule> findAll();

}
