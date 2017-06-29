package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.NotificationResult;

public interface NotificationResultRepository {

    void save(NotificationResult notificationResult);

    List<NotificationResult> findAll();

    NotificationResult findById(int id);

    void delete(NotificationResult notiResult);
}
