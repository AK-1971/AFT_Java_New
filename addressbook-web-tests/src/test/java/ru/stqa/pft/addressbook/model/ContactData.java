package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private final String firstname;
  private final String middlename;
  private final String lastname;
  private final String nickname;
  private final String company;
  private final String address;
  private final String home;
  private final String email;
  private final String notes;
  private String group;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(nickname, that.nickname) && Objects.equals(company, that.company) && Objects.equals(address, that.address) && Objects.equals(home, that.home) && Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname, nickname, company, address, home, email);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", address='" + address + '\'' +
            ", group='" + group + '\'' +
            '}';
  }

  public ContactData(String firstname, String middlename, String lastname, String nickname, String company,
                     String address, String home, String email, String notes, String group) {
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

  public String getGroup() {
    return group;
  }
}
