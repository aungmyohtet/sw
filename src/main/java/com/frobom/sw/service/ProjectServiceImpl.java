package com.frobom.sw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;
import com.frobom.sw.repository.MailAddressRepository;
import com.frobom.sw.repository.ProjectRepository;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MailAddressRepository mailAddressRepo;

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void setMailAddressRepo(MailAddressRepository mailAddressRepo) {
        this.mailAddressRepo = mailAddressRepo;
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

    @Override
    @Transactional
    public void update(Project project) {
        projectRepository.update(project);
    }

    @Override
    @Transactional
    public void delete(int id) {
        projectRepository.delete(id);
    }

    @Override
    @Transactional
    public void addMailAddressToProject(String address, String projName) {
        MailAddress mailAddress = mailAddressRepo.findByAddress(address);
        Project project = projectRepository.findByName(projName);
        if (mailAddress != null && project != null) {
            project.getMailAddresses().add(mailAddress);
            projectRepository.save(project);
        }
    }

    @Override
    @Transactional
    public boolean IsExistsMailAddress(MailAddress mailAddress, Project project) {
        Project proj = projectRepository.findByName(project.getName());
        MailAddress address = mailAddressRepo.findByAddress(mailAddress.getAddress());
        if (proj.getMailAddresses().contains(address)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<MailAddress> findMailAddressesByID(int id) {
        Project proj = projectRepository.findById(id);
        return proj.getMailAddresses();
    }

    @Override
    @Transactional
    public void deleteMailAddress(int pid, int mid) {
        Project proj = projectRepository.findById(pid);
        for (int i = 0; i < proj.getMailAddresses().size(); i++) {
            if (proj.getMailAddresses().get(i).getId().equals(mid)) {
                proj.getMailAddresses().remove(i);
            }
        }
    }

}
