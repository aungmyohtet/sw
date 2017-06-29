package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.AlertWordCount;

public interface AlertWordCountRepository {

    List<AlertWordCount> findAll();

    void delete(AlertWordCount alertWordCount);

}
