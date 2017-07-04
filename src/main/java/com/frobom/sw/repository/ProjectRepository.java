package com.frobom.sw.repository;

import java.util.List;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;

public interface ProjectRepository {

    void save(Project project);

    List<Project> findAll();

    Project findById(int id);

    Project findByName(String name);

    void update(Project project);

    void delete(Project project);

    Project isExistsMailAddress(MailAddress mailAddress, Project project);
}
