package com.frobom.sw.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "mail_address")
public class MailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @Column(unique = true)
    @Email
    @NotBlank(message = "Address cannot be empty.")
    private String address;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "mailAddresses")
    private List<Project> projects;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mailAddress")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MailPropertySetting> propertySettings;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "mailAddress")
    private List<Mail> mailList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<MailPropertySetting> getPropertySettings() {
        return propertySettings;
    }

    public void setPropertySettings(List<MailPropertySetting> propertySettings) {
        this.propertySettings = propertySettings;
    }

    public List<Mail> getMailList() {
        return mailList;
    }

    public void setMailList(List<Mail> mailList) {
        this.mailList = mailList;
    }
}
