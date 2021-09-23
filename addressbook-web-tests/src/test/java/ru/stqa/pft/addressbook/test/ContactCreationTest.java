package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactCreationTest extends TestBase {

  String groupName = new String(); //не создавал конструктора без группы, поэтому имя группы в контакт передавать нужно

  @BeforeMethod
  public void preconditions() {
    app.group().createIfNotExists();
    app.goTo().groupPage();
    /*List<GroupData> group = app.group().list();//выясняем имя группы в списке (берем первую)
    groupName = group.get(0).getGroupName(); //и передаем его в данные контакта*/
    Set<GroupData> group = app.group().all();
    groupName = group.iterator().next().getGroupName();
  }

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().setFirstname("Ivan").setMiddlename("Ivanovich").setLastname("Ivanov")
            .setNickname("Beetle").setCompany("NCC").setAddress("Moscow").setHome("123456789")
            .setEmail("asdf@mail.ru").setNotes("bla bla").setGroup(String.format("%s", groupName));
    app.contact().create(contact);
    app.goTo().homePage();
    Assert.assertEquals(app.contact().getCount(), before.size() + 1);
    Contacts after = app.contact().all();
    contact.setID(after.stream().mapToInt((c)-> c.getId()).max().getAsInt());
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.withAdded(contact)));
  }

}
