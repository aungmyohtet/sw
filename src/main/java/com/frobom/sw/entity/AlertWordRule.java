package com.frobom.sw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "alert_word_rule")
public class AlertWordRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "project_id")
    private Integer id;

    @OneToOne
    @MapsId
    private Project project;

    @Min(1)
    private int threshold;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
