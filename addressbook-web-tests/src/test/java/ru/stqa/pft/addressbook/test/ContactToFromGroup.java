package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Sets;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactToFromGroup extends TestBase{
  @BeforeMethod
  public void precondition() {
    app.goTo().groupPage();
    app.group().createIfNotExists();
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      List<GroupData> groups = app.group().list();
      Set<GroupData> groupDataSet = Sets.newHashSet(groups);

      ContactData contact = new ContactData().setLastname("Skywalker").setMiddlename("Veiderovich")
              .setFirstname("Luc").setNickname("Freak").setAddress("Galaxy").setHomePhone("123456789")
              .setAllEmail("asdf@mail.ru").setPhotoPath(new File("src\\test\\resources\\nafan.jpg"));
      app.contact().create(contact);
      //app.goTo().homePage();
    }
  }

  @Test(enabled = true)
  public void contactToGroup() {
    app.goTo().groupPage();
    app.group().all();
    app.goTo().homePage();

    GroupData groupThis = app.db().groups().iterator().next();
    /*Set<GroupData> groupSet = new HashSet<>();
    groupSet.add(groupThis);*/

    ContactData randomContact = app.db().contacts().iterator().next();

    if (randomContact.getGroups().size() != 0) {
      app.contact().deleteGroupFromContact(randomContact.getId(),
              randomContact.getGroups().iterator().next().getId());
    }

    //Костыль чтобы обновить контакт - т.к. иначе будет получен из кеша и assert не увидит, что контакт не в группе
    app.goTo().homePage();
    ContactData contactOutOfGroups;
    do {
      contactOutOfGroups = app.db().contacts().iterator().next(); }
    while (contactOutOfGroups.getId() != randomContact.getId());

    System.out.println(contactOutOfGroups.getGroups().size());

    MatcherAssert.assertThat(contactOutOfGroups.getGroups().size(),
            CoreMatchers.equalTo(0));

    app.contact().addContactToAnyGroup(contactOutOfGroups.getId());

    ContactData contactAddTofGroup;
    do {
      contactAddTofGroup = app.db().contacts().iterator().next(); }
    while (contactAddTofGroup.getId() != contactOutOfGroups.getId());

    MatcherAssert.assertThat(contactAddTofGroup.getGroups().size(),
            CoreMatchers.equalTo(1));

  }

  @Test(enabled = true)
  public void contactFromGroup() {
    app.goTo().groupPage();
    Groups groups = app.group().all();
    app.goTo().homePage();

    GroupData groupThis = app.db().groups().iterator().next();
    Set<GroupData> groupSet = new HashSet<>();
    groupSet.add(groupThis);

    ContactData randomContact = app.db().contacts().iterator().next();

    if (randomContact.getGroups().size() == 0) {
      app.contact().addContactToAnyGroup(randomContact.getId());
    }
    app.goTo().groupPage();
    app.goTo().homePage();

    ContactData contactInGroup;
    do {
      contactInGroup = app.db().contacts().iterator().next(); }
    while (contactInGroup.getId() != randomContact.getId());

    System.out.println(contactInGroup.getGroups().size());

    MatcherAssert.assertThat(contactInGroup.getGroups().size(),
            CoreMatchers.equalTo(1));

    app.contact().deleteGroupFromContact(contactInGroup.getId(),
            contactInGroup.getGroups().iterator().next().getId());

    //Обновляю контакт (чтобы не подхватился кешированный
    ContactData contactOutOfGroups;
    do {
      contactOutOfGroups = app.db().contacts().iterator().next(); }
    while (contactOutOfGroups.getId() != contactInGroup.getId());

    System.out.println(contactOutOfGroups.getGroups().size());

    MatcherAssert.assertThat(contactOutOfGroups.getGroups().size(),
            CoreMatchers.equalTo(0));

  }
}
