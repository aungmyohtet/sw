package com.frobom.sw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "mail_property_setting")
public class MailPropertySetting {

    @Id
    @Column(name = "mail_address_id")
    private Integer mailAddressId;

    @Id
    @Column(name = "mail_property_key_id")
    private Integer mailPropertyKeyId;

    private String value;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "mail_address_id", referencedColumnName = "id")
    private MailAddress mailAddress;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "mail_property_key_id", referencedColumnName = "id")
    private MailPropertyKey mailPropertyKey;

    public Integer getMailAddressId() {
        return mailAddressId;
    }

    public void setMailAddressId(Integer mailAddressId) {
        this.mailAddressId = mailAddressId;
    }

    public int getMailPropertyKeyId() {
        return mailPropertyKeyId;
    }

    public void setMailPropertyKeyId(int mailPropertyKeyId) {
        this.mailPropertyKeyId = mailPropertyKeyId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MailAddress getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(MailAddress mailAddress) {
        this.mailAddress = mailAddress;
    }

    public MailPropertyKey getMailPropertyKey() {
        return mailPropertyKey;
    }

    public void setMailPropertyKey(MailPropertyKey mailPropertyKey) {
        this.mailPropertyKey = mailPropertyKey;
    }

}
