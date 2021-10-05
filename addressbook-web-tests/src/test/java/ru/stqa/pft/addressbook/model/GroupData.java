package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity //эта аннотация объявляет класс привязанным к базе
@Table(name="group_list")
public class GroupData {

  @Id
  @Column(name="group_id")
  private int id = Integer.MAX_VALUE;
  public int getId() { return id; }
  public GroupData setId(int id) {
    this.id = id;
    return this;
  }

  @Expose
  @Column(name = "group_name")
  private String group_name;
  public String getGroupName() {
    return group_name;
  }
  public GroupData setName(String group_name) {
    this.group_name = group_name;
    return this;
  }

  @Expose
  @Column(name = "group_header")
  @Type(type = "text")
  private String header;
  public String getHeader() {
    return header;
  }
  public GroupData setHeader(String header) {
    this.header = header;
    return this;
  }

  @Expose
  @Column(name = "group_footer")
  @Type(type = "text")
  private String footer;
  public String getFooter() {
    return footer;
  }
  public GroupData setFooter(String footer) {
    this.footer = footer;
    return this;
  }

    public Contacts getContacts() {
    return new Contacts(contacts);
  }

  public void setContacts(Set<ContactData> contacts) {
    this.contacts = contacts;
  }

  @ManyToMany(mappedBy = "groups")
  private Set<ContactData> contacts = new HashSet<ContactData>(); //см. 7.6.


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
    return id == groupData.id && Objects.equals(group_name, groupData.group_name) && Objects.equals(header, groupData.header) && Objects.equals(footer, groupData.footer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, group_name, header, footer);
  }
}
