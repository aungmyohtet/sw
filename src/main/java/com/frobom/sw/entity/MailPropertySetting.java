/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.frobom.sw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "mail_property_setting")
public class MailPropertySetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "mail_address_id", referencedColumnName = "id")
    private MailAddress mailAddress;

    @Id
    @ManyToOne
    @JoinColumn(name = "mail_property_key_id", referencedColumnName = "id")
    private MailPropertyKey mailPropertyKey;

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
