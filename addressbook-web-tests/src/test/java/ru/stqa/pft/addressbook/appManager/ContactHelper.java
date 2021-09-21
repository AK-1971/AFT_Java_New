package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void fillContact(ContactData contactData, boolean itsContactCreation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHome());
    type(By.name("email"), contactData.getEmail());
    type(By.name("notes"), contactData.getNotes());

    if (itsContactCreation) {//3.8. проверка есть ли в форме кнопка добавления в группу (в модификации контакта ее нет)
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else { //3.8.если кнопка добавления в группу на форме модификации появилась - это баг и ниже проводится проверка
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initNewContact() {
    click(By.linkText("add new"));
  }

  public void submitContactForm() {
    click(By.name("submit"));
  }

  public void deleteContactAndCofirm() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
    wd.findElement(By.cssSelector("div.msgbox"));
  }

  public void selectContact() {
    click(By.name("selected[]"));
  }

  public void initEditContact() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void updateContactEdit() {
    click(By.name("update"));
  }

  public void create(ContactData contactData) {
    initNewContact();
    fillContact(contactData, true);
    submitContactForm();
  }

  public boolean isContactPresent() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getCount() {
    return wd.findElements(By.name("selected[]")).size();
  }
}
