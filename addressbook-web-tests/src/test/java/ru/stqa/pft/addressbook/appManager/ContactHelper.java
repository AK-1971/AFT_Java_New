package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  AppsManager manager;

  public ContactHelper(AppsManager manager) {
    super(manager.wd);
    this.manager = manager;
  }
  /*public ContactHelper(WebDriver wd) {
    super(wd);
  }*/

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

  public void modify(int index, ContactData modifyingContact) {
    chooseContactEdit(index);
    fillContact(modifyingContact, false);
    updateContactEdit();
  }

  public boolean isContactPresent() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> list() {
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

  public void delete(int index) {
    selectContact(index);
    deleteContactAndCofirm();
    manager.goTo().homePage();
  }

  public void createIfNotExists() {
    //Проверяем также наличие группы - создаем при отсутствии
    manager.goTo().groupPage();
    if (!manager.group().isThereGroup()) {
      manager.group().create(new GroupData("test1", "test2", "test3"));
    }
    manager.goTo().homePage();
    if (!isContactPresent()) {
      manager.goTo().groupPage();
      List<GroupData> group = manager.group().list();//выясняем имя группы в списке (берем первую)
      String groupName = group.get(0).getGroupName(); //и передаем его в данные контакта
      manager.goTo().homePage();
      create(new ContactData("Ivan", "Ivanovich", "Ivanov",
              "Beetle", "NCC", "Moscow", "123456789",
              "asdf@mail.ru", "bla bla", String.format("%s", groupName)));
      manager.goTo().homePage();
    }
  }

}
