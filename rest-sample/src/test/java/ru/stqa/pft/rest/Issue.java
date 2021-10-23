package ru.stqa.pft.rest;

import java.util.Objects;

public class Issue {
  private int id;
  private String subject;
  private String description;
  private String status;

  public String getStatus() {
    return status;
  }

  public Issue setStatus(String status) {
    this.status = status;
    return this;
  }

  public int getId() {
    return id;
  }

  public Issue setId(int id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public Issue setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Issue setDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Issue issue = (Issue) o;
    return id == issue.id && Objects.equals(subject, issue.subject) && Objects.equals(description, issue.description) && Objects.equals(status, issue.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, subject, description, status);
  }

  @Override
  public String toString() {
    return "Issue{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", description='" + description + '\'' +
            ", status='" + status + '\'' +
            '}';
  }
}
