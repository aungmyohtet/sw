package com.frobom.sw.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mail_address_id")
    private MailAddress mailAddress;

    @OneToOne
    @PrimaryKeyJoinColumn
    private AlertWordCount alertWordCount;

    private Date date;

    @Column(columnDefinition = "text")
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @Column(name = "sender_mail_address")
    private String senderMailAddress;

    private Boolean analyzed;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "mail")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MailRawDataPath> mailRawDatapaths;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MailAddress getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(MailAddress mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderMailAddress() {
        return senderMailAddress;
    }

    public void setSenderMailAddress(String senderMailAddress) {
        this.senderMailAddress = senderMailAddress;
    }

    public Boolean getAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(Boolean analyzed) {
        this.analyzed = analyzed;
    }

    public List<MailRawDataPath> getMailRawDatapaths() {
        return mailRawDatapaths;
    }

    public void setMailRawDatapaths(List<MailRawDataPath> mailRawDatapaths) {
        this.mailRawDatapaths = mailRawDatapaths;
    }

    public AlertWordCount getAlertWordCount() {
        return alertWordCount;
    }

    public void setAlertWordCount(AlertWordCount alertWordCount) {
        this.alertWordCount = alertWordCount;
    }

}
