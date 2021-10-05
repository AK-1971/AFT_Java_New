package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.Objects;

@Entity
@Table(name = "addressbook") //привязка к базе
public class ContactData {

  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE; //4.7. и в 4.10 - перенос сюда см. 5.4.

  @Expose
  @Column(name = "firstName")
  private String firstname;

  @Column(name = "middlename")
  private String middlename;

  @Expose
  @Column(name = "lastName")
  private String lastname;

  @Expose
  @Column(name = "nickname")
  private String nickname;
  @Expose
  @Column(name = "company")
  private String company;

  @Column(name = "homepage")
  @Type(type = "text")
  private String homepage;

  @Expose
  @Column(name = "address")
  @Type(type = "text")
  private String address;

  @Expose
  @Transient
  private String allPhones;
  @Column(name = "home")
  @Type(type = "text")
  private String homePhone;
  @Expose
  @Column(name = "mobile")
  @Type(type = "text")
  private String mobilePhone;
  @Expose
  @Column(name = "work")
  @Type(type = "text")
  private String workPhone;
  @Column(name = "fax")
  @Type(type = "text")
  private String faxNumber;
  @Expose
  @Transient
  private String allEmail;
  @Expose
  @Column(name = "email")
  @Type(type = "text")
  private String email;
  @Transient
  private String email2;
  @Transient
  private String email3;
  @Transient
  private String notes;
  @Transient
  private int group;
  /*@Expose
  public File photo;

  public File getPhoto() {
    return photo;
  }

  public ContactData setPhoto(File photo) {
    this.photo = photo;
    return this;
  }*/
  @Expose
  @Column(name = "photo")
  @Type(type = "text")
  private String photoPath;

 /* public ContactData setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
    return this;
  }

  public File getPhotoPath() { return new File(photoPath); }*/

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(nickname, that.nickname) && Objects.equals(company, that.company) && Objects.equals(address, that.address) && Objects.equals(mobilePhone, that.mobilePhone) && Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, lastname, nickname, company, address, mobilePhone, email);
  }

  public File getPhotoPath() {
    if (photoPath != null) {
      return new File(photoPath);
    } else {
      return null;
    }
  }

  public ContactData setPhotoPath(File photoPath) {
    this.photoPath = photoPath.getPath();
    return this;
  } //вариант (!?!) когда передаем аргументом файл

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

  public ContactData setHomepage(String homepage) {
    this.homepage = homepage;
    return this;
  }
  public String getHomepage() {return homepage;}

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
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", address='" + address + '\'' +
            '}';
  }

}
