package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

  AppsManager manager;

  public ContactHelper(AppsManager manager) {
    super(manager.wd);
    this.manager = manager;
  }

  public void fillContact(ContactData contactData, boolean itsContactCreation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("email"), contactData.getAllEmail());
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

  public void selectContactByID(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }


  public void chooseContactEdit(int id) {
    //wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    wd.findElement(By.cssSelector("a[href=\"edit.php?id=" + id + "\"]")).click();
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
    contactsCache = null;
  }

  public void modify(int index, ContactData modifyingContact) {
    chooseContactEdit(index);
    fillContact(modifyingContact, false);
    updateContactEdit();
    contactsCache = null;
  }

  public void modify(ContactData modifyingContact) {
    chooseContactEdit(modifyingContact.getId());
    fillContact(modifyingContact, false);
    updateContactEdit();
    contactsCache = null;
  }


  public boolean isContactPresent() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void delete(int index) {
    selectContact(index);
    deleteContactAndCofirm();
    contactsCache = null;
    manager.goTo().homePage();
  }

  public void delete(ContactData contact) {
    selectContactByID(contact.getId());
    deleteContactAndCofirm();
    contactsCache = null;
    manager.goTo().homePage();
  }


  public void createIfNotExists() {
    //Проверяем также наличие группы - создаем при отсутствии
    manager.goTo().groupPage();
    if (!manager.group().isThereGroup()) {
      manager.group().create(new GroupData().setName("test2").setHeader("header2").setFooter("footer"));
    }
    manager.goTo().homePage();
    if (!isContactPresent()) {
      manager.goTo().groupPage();
      //List<GroupData> group = manager.group().list();//выясняем имя группы в списке (берем первую)
      Set<GroupData> group = manager.group().all();
      String groupName = group.iterator().next().getGroupName(); //и передаем его в данные контакта
      manager.goTo().homePage();
      create(new ContactData().setFirstname("Ivan").setMiddlename("Ivanovich").setLastname("Ivanov")
              .setNickname("Beetle").setCompany("NCC").setAddress("Moscow").setHomePhone("123456789")
              .setAllEmail("asdf@mail.ru").setNotes( "bla bla").setGroup(String.format("%s", groupName)));
      manager.goTo().homePage();
    }
  }

  public ContactData infoFromEditForm(ContactData contact) { //5.10
    initContactModifyByID(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String adress = wd.findElement(By.name("address")).getText();
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    //String email3 = wd.findElement(By.name("email3")).getAttribute("value");

    wd.navigate().back();
    return new ContactData().setId(contact.getId()).setFirstname(firstname).setLastname(lastname)
            .setAddress(adress).setHomePhone(home).setMobilePhone(mobile).setWorkPhone(work)
            .setEmail(email).setEmail2(email2);
  }

  private void initContactModifyByID(int id) { //5.10
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']",id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();

    //Примеры локаторов на клик редактирования контакта (карандаш в строке контакта)
    // Карандаш -8й элемент; css считает с нуля, xpath - с единицы
    //wd.findElement(By.xpath(String.format("//input[@value='%s']/../../tf[8]/a", id)));
    //wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id)));
    //wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id)));
  }

  private Contacts contactsCache = null; //5.7.

  public Contacts all() {
    if (contactsCache != null){ //5.7. если кеш не пустой, значит список уже прочитан, находится в кеше, дальнейшее действие метод all(0 прекращается
      return new Contacts(contactsCache); //возвращаем не сам кеш, а его копию - на всякий случай, чтобы сам кеш не был случайно испорчен
    }
    //Contacts contacts = new Contacts();
    contactsCache = new Contacts(); //создаем список контактов на странице - считываем в кеш
    List<WebElement> elenents = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
    for (WebElement element : elenents) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String adress = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      /*String[] emails = cells.get(4).getText().split("\n"); //5.10
      String[] phones = cells.get(5).getText().split("\n"); //5.10*/
      ContactData contact = new ContactData().setId(id).setFirstname(firstname).setLastname(lastname)
              .setAddress(adress).setAllPhones(allPhones).setAllEmail(allEmails);
      contactsCache.add(contact);
    }
    return contactsCache;
  }

  //Так у АБ
  public Set<ContactData> allLikeAB() {
    Set<ContactData> contacts = new HashSet<ContactData>();
    List<WebElement> rows = wd.findElements(By.name("entry"));

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String adress = cells.get(3).getText();
      String email = cells.get(4).getText();
      String phones = cells.get(5).getText();
      contacts.add(new ContactData().setId(id).setFirstname(firstname).setLastname(lastname)
              .setAddress(adress).setAllEmail(email));
    }
    return contacts;
  }

  public Contacts allAK() { //так было у меня до 5.10
    if (contactsCache != null){ //5.7. если кеш не пустой, значит список уже прочитан, находится в кеше, дальнейшее действие метод all(0 прекращается
      return new Contacts(contactsCache); //возвращаем не сам кеш, а его копию - на всякий случай, чтобы сам кеш не был случайно испорчен
    }
    //Contacts contacts = new Contacts();
    contactsCache = new Contacts(); //создаем список контактов на странице - считываем в кеш
    List<WebElement> elenents = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
    for (WebElement element : elenents) {
      List<WebElement> cells = element.findElements(By.tagName("td"));

      /*String name = element.getText(); Так неправильно! - берутся все записи в строке таблицы и присваиваются в firstname
      String id = element.findElement(By.tagName("input")).getAttribute("value"); //4.7.на 08.00 поиск одного элемента внутри другого
      ContactData contact = new ContactData(id, name, null, null, null, null,
              null, null, null, null, null);*/
      String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      String adress = element.findElement(By.cssSelector("td:nth-child(4)")).getText();
      String phones = element.findElement(By.cssSelector("a:nth-child(1)")).getText();
      String email = element.findElement(By.cssSelector("td:nth-child(5)")).getText();
      //String email = element.findElement(By.cssSelector("a:nth-child(3)")).getText();
      //#maintable > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(5) > a:nth-child(1) css локаторы по полю Allemails
      //#maintable > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(5) > a:nth-child(1) css локаторы по полю Allemails
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

      ContactData contact = new ContactData().setId(id).setFirstname(firstname).setLastname(lastname)
              .setAddress(adress).setAllPhones(phones);
      contactsCache.add(contact);
    }
    return contactsCache;
  }

  /*public Set<ContactData> all() {
    Set<ContactData> contacts = new HashSet<ContactData>();
    List<WebElement> elenents = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
    for (WebElement element : elenents) {
      String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      String adress = element.findElement(By.cssSelector("td:nth-child(4)")).getText();
      String phone = element.findElement(By.cssSelector("a:nth-child(1)")).getText();
      //String email = element.findElement(By.cssSelector("a:nth-child(3)")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData().setID(id).setFirstname(firstname).setLastname(lastname)
              .setAddress(adress).setHome(phone);
      contacts.add(contact);
    }
    return contacts;
  }

  public List<ContactData> list() {//до 5.5. пользовались этим методом
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elenents = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
    for (WebElement element : elenents) {
      String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      String adress = element.findElement(By.cssSelector("td:nth-child(4)")).getText();
      String phone = element.findElement(By.cssSelector("a:nth-child(1)")).getText();
      //String email = element.findElement(By.cssSelector("a:nth-child(3)")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData().setID(id).setFirstname(firstname).setLastname(lastname)
              .setAddress(adress).setHome(phone);
      contacts.add(contact);
    }
    return contacts;
  }*/


}
