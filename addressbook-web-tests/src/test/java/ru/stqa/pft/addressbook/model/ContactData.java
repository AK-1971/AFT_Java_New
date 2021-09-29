package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;

import java.io.File;
import java.util.Objects;

public class ContactData {
  private int id = Integer.MAX_VALUE; //4.7. и в 4.10 - перенос сюда см. 5.4.
  @Expose
  private String firstname;
  private String middlename;
  @Expose
  private String lastname;
  private String nickname;
  private String company;
  @Expose
  private String address;
  @Expose
  private String allPhones;
  private String homePhone;
  private String mobilePhone;
  private String workPhone;
  private String faxNumber;
  @Expose
  private String allEmail;
  private String email;
  private String email2;
  private String email3;
  private String notes;
  private int group;
  public File photo;

  public File getPhoto() {
    return photo;
  }

  public ContactData setPhoto(File photo) {
    this.photo = photo;
    return this;
  }


  public  ContactData setId(int id){
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

  public ContactData setAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData setHomePhone(String homePhone) {
    this.homePhone = homePhone;
    return this;
  }

  public ContactData setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
    return this;
  }

  public ContactData setWorkPhone(String workPhone) {
    this.workPhone = workPhone;
    return this;
  }

  public ContactData setFaxNumber(String faxNumber) {
    this.faxNumber = faxNumber;
    return this;
  }

  public ContactData setAllEmail(String email) {
    this.allEmail = email;
    return this;
  }

  public ContactData setEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData setEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData setEmail3(String email3) {
    this.email2 = email3;
    return this;
  }
  public ContactData setNotes(String notes) {
    this.notes = notes;
    return this;
  }

  public ContactData setGroup(int group) {
    this.group = group;
    return this;
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

  public String getAllPhones() { return allPhones; }

  public String getHomePhone() {
    return homePhone;
  }

  public String getMobilePhone() { return mobilePhone; }

  public String getWorkPhone() { return workPhone; }

  public String getFaxNumber() { return faxNumber; }

  public String getAllEmail() {
    return allEmail;
  }

  public String getEmail() { return email; }

  public String getEmail2() { return email2; }

  public String getEmail3() { return email3; }

  public String getNotes() {
    return notes;
  }

  public int getGroup() { return group; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(address, that.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, lastname, address);
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
            ", home='" + homePhone + '\'' +
            ", email='" + allEmail + '\'' +
            ", notes='" + notes + '\'' +
            ", group='" + group + '\'' +
            '}';
  }

}
