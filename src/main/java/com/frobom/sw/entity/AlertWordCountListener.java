package com.frobom.sw.entity;

import javax.persistence.Column;

import javax.persistence.MapsId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alert_word_count_listener")
public class AlertWordCountListener {

    @Id
    @Column(name = "mailaddress_id")
    private Integer id;

    @OneToOne
    @MapsId
    private MailAddress mailAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MailAddress getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(MailAddress mailAddress) {
        this.mailAddress = mailAddress;
    }

}
