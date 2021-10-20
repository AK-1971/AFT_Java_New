package ru.stqa.pft.mantis.model;

import biz.futureware.mantis.rpc.soap.client.ObjectRef;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "mantis_bug_table")
public class IssueInfo {

  @Id
  @Column(name = "id")
  private BigInteger id;
  @Column(name = "summary")
  private String summary;
  @Transient
  private String description;
  @Column(name = "resolution")
  private ObjectRef resolution;
  @Column(name = "project")
  private Project project;

  public BigInteger getId() {
    return id;
  }

  public IssueInfo setId(BigInteger id) {
    this.id = id;
    return this;
  }

  public String getSummary() {
    return summary;
  }

  public IssueInfo setSummary(String summary) {
    this.summary = summary;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public IssueInfo setDescription(String description) {
    this.description = description;
    return this;
  }

  public Project getProject() {
    return project;
  }

  public IssueInfo setProject(Project project) {
    this.project = project;
    return this;
  }

  public ObjectRef getResolution() {return resolution;}

  public IssueInfo setResolution(ObjectRef resolution) {
    this.resolution = resolution;
    return this;
  }

  @Override
  public String toString() {
    return "Issue{" +
            "id=" + id +
            ", summary='" + summary + '\'' +
            ", description='" + description + '\'' +
            ", resolution=" + resolution +
            ", project=" + project +
            '}';
  }

}
