package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTest extends TestBase {

  String groupName = new String(); //не создавал конструктора без группы, поэтому имя группы в контакт передавать нужно

  @BeforeMethod
  public void preconditions() {
    app.group().createIfNotExists();
    app.goTo().groupPage();
    List<GroupData> group = app.group().list();//выясняем имя группы в списке (берем первую)
    groupName = group.get(0).getGroupName(); //и передаем его в данные контакта
  }

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData().setFirstname("Ivan").setMiddlename("Ivanovich").setLastname("Ivanov")
            .setNickname("Beetle").setCompany("NCC").setAddress("Moscow").setHome("123456789")
            .setEmail("asdf@mail.ru").setNotes("bla bla").setGroup(String.format("%s", groupName));
    app.contact().create(contact);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    /*int maxID = 0;
    for(ContactData с : after) {
      if(maxID < с.getId()) {
        maxID = с.getId();
      }
    }
    contact.setId(maxID);*/
    //contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
  }

}
