package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

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

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void chooseContactEdit(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void contactEdit(int id) {
    wd.findElement(By.cssSelector("a[href=\"edit.php?id=" + id + "\"]")).click();
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

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elenents = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
    for (WebElement element : elenents) {
      /*String name = element.getText(); Так неправильно! - берутся все записи в строке таблицы и присваиваются в firstname
      String id = element.findElement(By.tagName("input")).getAttribute("value"); //4.7.на 08.00 поиск одного элемента внутри другого
      ContactData contact = new ContactData(id, name, null, null, null, null,
              null, null, null, null, null);*/
      String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      String adress = element.findElement(By.cssSelector("td:nth-child(4)")).getText();
      String phone = element.findElement(By.cssSelector("a:nth-child(1)")).getText();
      //String email = element.findElement(By.cssSelector("a:nth-child(3)")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData(id, firstname, lastname, adress, phone, null);
      contacts.add(contact);
    }
    return contacts;
  }
}
