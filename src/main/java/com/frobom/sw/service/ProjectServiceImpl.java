package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.Project;
import com.frobom.sw.repository.ProjectRepository;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public void save(Project project) {
    projectRepository.save(project);
    }

    @Override
    @Transactional
    public List<Project> findAll() {
       return projectRepository.findAll();
    }

    @Override
    @Transactional
    public Project findById(int id) {
      return projectRepository.findById(id);
    }

    @Override
    @Transactional
    public Project findByName(String name) {
       return projectRepository.findByName(name);
    }

}
