package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.Project;

public interface ProjectRepository {

    void save(Project project);

    List<Project> findAll();

    Project findById(int id);
    
    Project findByName(String name);
}
