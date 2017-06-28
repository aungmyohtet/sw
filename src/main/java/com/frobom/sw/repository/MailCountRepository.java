package com.frobom.sw.repository;

import java.util.Date;
import java.util.List;

import com.frobom.sw.entity.MailCount;
import com.frobom.sw.entity.Project;

public interface MailCountRepository {

    void add(MailCount mailCount);

    List<MailCount> findAll();

    MailCount findById(int id);
    
    MailCount findByProjectAndDate(Project project, Date date);
    
    void update(MailCount mailCount);
}
