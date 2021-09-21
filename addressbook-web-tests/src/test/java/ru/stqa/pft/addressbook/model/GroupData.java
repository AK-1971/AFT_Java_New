package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class GroupData {
  private final String id;
  private final String group_name;
  private final String header;
  private final String footer;


  @Override
  public String toString() {
    return "GroupData{" +
            "id=" + id +
            ", group_name='" + group_name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GroupData groupData = (GroupData) o;
    return Objects.equals(id, groupData.id) && Objects.equals(group_name, groupData.group_name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, group_name);
  }

  public GroupData(String group_name, String header, String footer) { //4.7. на 09.30
    this.id = null;
    this.group_name = group_name;
    this.header = header;
    this.footer = footer;
  }

  public GroupData(String id, String group_name, String header, String footer) {
    this.id = id;
    this.group_name = group_name;
    this.header = header;
    this.footer = footer;
  }


  public String getId() { return id; }

  public String getGroup_name() {
    return group_name;
  }

  public String getHeader() {
    return header;
  }

  public String getFooter() {
    return footer;
  }

}
