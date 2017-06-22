package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.Project;

public interface ProjectService {

    void save(Project project);

    List<Project> findAll();

    Project findById(int id);

    Project findByName(String name);
}
