package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class GroupData {

  private int id;
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  private final String group_name;
  public String getGroupName() {
    return group_name;
  }

  private final String header;
  public String getHeader() {
    return header;
  }

  private final String footer;
  public String getFooter() {
    return footer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GroupData groupData = (GroupData) o;
    return Objects.equals(group_name, groupData.group_name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(group_name);
  }

  public GroupData(String group_name, String header, String footer) { //4.7. на 09.30
    this.id = Integer.MAX_VALUE;
    this.group_name = group_name;
    this.header = header;
    this.footer = footer;
  }

  public GroupData(int id, String group_name, String header, String footer) {
    this.id = id;
    this.group_name = group_name;
    this.header = header;
    this.footer = footer;
  }

  @Override
  public String toString() {
    return "GroupData{" +
            "id=" + id +
            ", group_name='" + group_name + '\'' +
            '}';
  }


}
