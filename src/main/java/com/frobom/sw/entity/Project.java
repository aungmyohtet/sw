package com.frobom.sw.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    @NotBlank(message = "Project name cannot be empty.")
    private String name;

    @ManyToMany
    @JoinTable(name = "target_mail", joinColumns = { @JoinColumn(name = "project_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "mail_address_id", referencedColumnName = "id") })
    private Set<MailAddress> mailAddresses;

    @OneToMany(mappedBy = "project")
    private List<MailCount> mailCountList;

    @OneToMany(mappedBy = "project")
    private List<NotificationResult> notificationResultList;

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

    public Set<MailAddress> getMailAddresses() {
        return mailAddresses;
    }

    public void setMailAddresses(Set<MailAddress> mailAddresses) {
        this.mailAddresses = mailAddresses;
    }

    public List<MailCount> getMailCountList() {
        return mailCountList;
    }

    public void setMailCountList(List<MailCount> mailCountList) {
        this.mailCountList = mailCountList;
    }

    public List<NotificationResult> getNotificationResultList() {
        return notificationResultList;
    }

    public void setNotificationResultList(List<NotificationResult> notificationResultList) {
        this.notificationResultList = notificationResultList;
    }
}
