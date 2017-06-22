package com.frobom.sw.service;

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;

public interface AddMailAddressToProjectService {

    void addMailAddressToProject(String address, String projName);

    boolean IsExistsMailAddress(MailAddress mailAddress, Project project);

}
