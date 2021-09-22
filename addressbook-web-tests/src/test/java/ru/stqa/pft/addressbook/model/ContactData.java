package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id = Integer.MAX_VALUE; //4.7. и в 4.10 - перенос сюда см. 5.4.
  private String firstname;
  private String middlename;
  private String lastname;
  private String nickname;
  private String company;
  private String address;
  private String home;
  private String email;
  private String notes;
  private String group;


  public  ContactData setID(int id){
    this.id = id;
    return this;
  }

  public ContactData setFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public ContactData setMiddlename(String middlename) {
    this.middlename = middlename;
    return this;
  }

  public ContactData setLastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public ContactData setNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public ContactData setCompany(String company) {
    this.company = company;
    return this;
  }

  public ContactData setAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData setHome(String home) {
    this.home = home;
    return this;
  }

  public ContactData setEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData setNotes(String notes) {
    this.notes = notes;
    return this;
  }

  public ContactData setGroup(String group) {
    this.group = group;
    return this;
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }


  public String getFirstname() {
    return firstname;
  }

  public String getMiddlename() {
    return middlename;
  }

  public String getLastname() {
    return lastname;
  }

  public String getNickname() {
    return nickname;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getHome() {
    return home;
  }

  public String getEmail() {
    return email;
  }

  public String getNotes() {
    return notes;
  }

  public String getGroup() { return group; }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", middlename='" + middlename + '\'' +
            ", lastname='" + lastname + '\'' +
            ", nickname='" + nickname + '\'' +
            ", company='" + company + '\'' +
            ", address='" + address + '\'' +
            ", home='" + home + '\'' +
            ", email='" + email + '\'' +
            ", notes='" + notes + '\'' +
            ", group='" + group + '\'' +
            '}';
  }

}
