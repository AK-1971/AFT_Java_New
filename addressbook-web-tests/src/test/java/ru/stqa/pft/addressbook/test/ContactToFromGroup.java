package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Sets;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ContactToFromGroup extends TestBase {
  @BeforeMethod
  public void precondition() {
    app.goTo().groupPage();
    app.group().createIfNotExists();
    app.goTo().homePage();
    app.contact().createIfNotExists();
    if (app.db().contacts().size() == 0) {
      List<GroupData> groups = app.group().list();
      Set<GroupData> groupDataSet = Sets.newHashSet(groups);

      ContactData contact = new ContactData().setLastname("Created").setMiddlename("From")
              .setFirstname("Precondition").setNickname("ContactToFromGroup").setAddress("Galaxy")
              .setHomePhone("123456789").setAllEmail("asdf@mail.ru")
              .setPhotoPath(new File("src\\test\\resources\\nafan.jpg"));
      app.contact().create(contact);
    }
  }

  @Test(enabled = true)
  public void contactToGroup() {
    app.goTo().groupPage();
    app.group().all();
    int allGroupCount = app.group().count();

    app.goTo().homePage();

    ContactData anyContact = null;
    ContactData contactAddToGroup = anyContact;
    GroupData groupInContact = null;
    GroupData groupToAdd = groupInContact;

    Groups groupsList = app.db().groups();
    GroupData[] groupsArray = new GroupData[groupsList.size()];
    groupsList.toArray(groupsArray);

    Contacts contactsList = app.db().contacts();
    ContactData[] contactsArray = new ContactData[contactsList.size()];
    contactsList.toArray(contactsArray);

    int i = 0; //счетчик для определения есть ли контакты которые могут быть добавлены в группу

    for(int c = 0; c < contactsList.size(); c++) {
      if (contactsArray[c].getGroups().size() != allGroupCount) {
        i++;
        contactAddToGroup = contactsArray[c];
        break;
      }
    }

    if (i == 0) {// если true - значит создаем новый контакт
      ContactData contact = new ContactData().setLastname("Created").setMiddlename("From")
              .setFirstname("Precondition").setNickname("ContactToFromGroup").setAddress("Galaxy")
              .setHomePhone("123456789").setAllEmail("asdf@mail.ru")
              .setPhotoPath(new File("src\\test\\resources\\nafan.jpg"));
      app.contact().create(contact);
      contactAddToGroup = contact;
    }

    //поиск группы в общем списке в которую найденный контакт не входит
    for (int k = 0; k < groupsList.size(); k++) {//сравниваем группу из общего списка с группой из списка групп контакта
      int g = 0;

      Iterator<GroupData> groupsIterator = contactAddToGroup.getGroups().iterator();//создаем список групп контакта
      while (groupsIterator.hasNext()) {
        if (groupsIterator.next().getId() == groupsArray[k].getId()) {
          g++;//контакт уже состоит в этой группе; проверяем следующую группу контакта
        }
      }
      if (g == 0) {
        groupToAdd = groupsArray[k];
        break;
      }
    }

    Groups beforeInContact = contactAddToGroup.getGroups();
    app.contact().addContactToGroup(contactAddToGroup.getId(), groupToAdd.getId());
    //Костыль чтобы обновить контакт - т.к. иначе будет получен из кеша и assert не увидит, что контакт не в группе
    app.goTo().homePage();
    ContactData contactWithNewGroups;
    do {
      contactWithNewGroups = app.db().contacts().iterator().next(); }
    while (contactWithNewGroups.getId() != contactAddToGroup.getId());

    Groups afterInContact = contactWithNewGroups.getGroups();

    MatcherAssert.assertThat(afterInContact, CoreMatchers.equalTo(beforeInContact.withAdded(groupToAdd)));
  }

  @Test(enabled = true)
  public void contactFromGroup() {
    //app.goTo().groupPage();
    //Groups groups = app.group().all();//считываю список групп
    app.goTo().homePage();

    Groups groupsList = app.db().groups();
    GroupData[] groupsArray = new GroupData[groupsList.size()];
    groupsList.toArray(groupsArray);

    Contacts contactsList = app.db().contacts();
    ContactData[] contactsArray = new ContactData[contactsList.size()];
    contactsList.toArray(contactsArray);

    //ContactData contactInGroup;
    ContactData contactInGroup = null;

    int i = 0; //счетчик для определения есть ли контакты которые могут быть добавлены в группу

    for(int c = 0; c < contactsList.size(); c++) { //в цикле проверяем состоит ли контакт в группе
      if (contactsArray[c].getGroups().size() != 0) { //если состоит используем его
        i++;
        contactInGroup = contactsArray[c];
        break;
      }
    }

    if (i == 0) { //если в предыдущем цикле не нашлось ни одного контакта с группой, добавляем
      contactInGroup = contactsArray[0];// рандомный контакт (1й - вдруг он всего 1) в рандомную группу
      app.contact().addContactToAnyGroup(contactInGroup.getId());
    }

    System.out.println(contactInGroup.getGroups().size());
    Groups beforeGroupsList = contactInGroup.getGroups();

    GroupData deletedGroup = contactInGroup.getGroups().iterator().next();
    int deletedGroupId = deletedGroup.getId();
    app.contact().deleteGroupFromContact(contactInGroup.getId(), deletedGroupId);

    //Обновляю контакт (чтобы не подхватился кешированный
    ContactData contactOutOfGroups;
    do {
      contactOutOfGroups = app.db().contacts().iterator().next();
    }
    while (contactOutOfGroups.getId() != contactInGroup.getId());

    System.out.println(contactOutOfGroups.getGroups().size());

    Groups afterGroupsList = contactOutOfGroups.getGroups();

    MatcherAssert.assertThat(afterGroupsList, CoreMatchers.equalTo(beforeGroupsList.withOut(deletedGroup)));
  }

  //Ниже - старые тесты - в них я брал произвольный контакт и в тесте для удаления из группы проверял - если
  //контакт на в группе - то сначала добавлял, затем удалял, при добавлении из группы - порядок обратный
  @Test(enabled = true)
  public void contactToGroupOld() {//В этом тесте берется произвольный контакт, если он уже в группе, то удаляется
    app.goTo().groupPage(); // из группы, а потом добавлеяем - так делал раньше
    app.group().all();
    app.goTo().homePage();

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
  public void contactFromGroupOld() { //произвольный контакт, если не имеет группы, то сначала добавляем,
    app.goTo().groupPage();           //потом удаляем - так было сделано в 1й попытке
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
