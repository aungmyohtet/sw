package com.frobom.sw.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mail_count_rule")
public class MailCountRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @NotNull(message = "Threshold should not be empty")
    @Min(1)
    private Integer threshold;

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
