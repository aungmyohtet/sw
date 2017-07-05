package com.frobom.sw.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "mail_property_setting")
public class MailPropertySetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "mail_address_id")
    private MailAddress mailAddress;

    @Id
    @ManyToOne
    @JoinColumn(name = "mail_property_key_id")
    private MailPropertyKey mailPropertyKey;

    @NotBlank(message = "Value cannot be blank.")
    private String value;

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
