package com.frobom.sw.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mail_property_key")
public class MailPropertyKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "mailPropertyKey")
    private List<MailPropertySetting> propertySettings;

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

    public List<MailPropertySetting> getPropertySettings() {
        return propertySettings;
    }

    public void setPropertySettings(List<MailPropertySetting> propertySettings) {
        this.propertySettings = propertySettings;
    }

}
