package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;

public interface ProjectService {

    void save(Project project);

    List<Project> findAll();

    Project findById(int id);

    Project findByName(String name);

    void update(Project project);

    void delete(int id);

    void addMailAddressToProject(String address, int id);

    boolean IsExistsMailAddress(MailAddress mailAddress, Project project);

    List<MailAddress> findMailAddressesByID(int projectId);

    void deleteMailAddress(int pid, int mid);
}
