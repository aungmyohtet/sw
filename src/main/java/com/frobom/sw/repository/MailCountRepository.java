package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.MailCount;

public interface MailCountRepository {

    List<MailCount> findAll();

    void delete(MailCount mailCount);

}
