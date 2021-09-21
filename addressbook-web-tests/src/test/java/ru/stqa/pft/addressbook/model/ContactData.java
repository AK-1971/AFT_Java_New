package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id;
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



  public ContactData(String firstname, String middlename, String lastname, String nickname, String company,
                     String address, String home, String email, String notes, String group) {
    this.id = 0; //4.7.
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.nickname = nickname;
    this.company = company;
    this.address = address;
    this.home = home;
    this.email = email;
    this.notes = notes;
    this.group = group;
  }

  public ContactData(int id, String firstname, String middlename, String lastname, String nickname, String company,
                     String address, String home, String email, String notes, String group) {
    this.id = id;
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.nickname = nickname;
    this.company = company;
    this.address = address;
    this.home = home;
    this.email = email;
    this.notes = notes;
    this.group = group;
  }

  public ContactData(int id, String firstname, String lastname, String adress, String homePhone,
                      String group) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = adress;
    this.home = homePhone;
    this.group = group;
  }

  public int getId() { return id; }

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
    return Objects.equals(id, that.id) && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, lastname);
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
