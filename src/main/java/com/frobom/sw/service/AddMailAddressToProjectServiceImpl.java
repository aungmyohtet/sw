package com.frobom.sw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;
import com.frobom.sw.repository.MailAddressRepository;
import com.frobom.sw.repository.ProjectRepository;

@Service("addMailAddressToProjService")
public class AddMailAddressToProjectServiceImpl implements AddMailAddressToProjectService {

    @Autowired
    private ProjectRepository projRepo;

    @Autowired
    private MailAddressRepository mailAddressRepo;

    public void setProjRepo(ProjectRepository projRepo) {
        this.projRepo = projRepo;
    }

    public void setMailAddressRepo(MailAddressRepository mailAddressRepo) {
        this.mailAddressRepo = mailAddressRepo;
    }

    @Override
    @Transactional
    public void addMailAddressToProject(String address, String projName) {
        MailAddress mailAddress = mailAddressRepo.findByAddress(address);
        Project project = projRepo.findByName(projName);
        if (mailAddress != null && project != null) {
            project.getMailAddresses().add(mailAddress);
            projRepo.save(project);
        }
    }

    @Override
    @Transactional
    public boolean IsExistsMailAddress(MailAddress mailAddress, Project project) {
        Project proj = projRepo.findByName(project.getName());
        MailAddress address = mailAddressRepo.findByAddress(mailAddress.getAddress());
        if (proj.getMailAddresses().contains(address)) {
            return true;
        }
        return false;
    }
}
