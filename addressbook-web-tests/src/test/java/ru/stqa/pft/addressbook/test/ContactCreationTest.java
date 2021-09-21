package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class ContactCreationTest extends TestBase {

 /* @BeforeMethod
  public void preconditions(){ //поскольку указано обязательное добавление в группу м именем test1, создаем
    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().create(new GroupData("test1", "header1", "footer1"));
  }*/

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Ivan", "Ivanovich", "Ivanov",
            "Beetle", "NCC", "Moscow", "123456789",
            "asdf@mail.ru", "bla bla", "test1");
    app.getContactHelper().create(contact);
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    /*int maxID = 0;
    for(ContactData с : after) {
      if(maxID < с.getId()) {
        maxID = с.getId();
      }
    }
    contact.setId(maxID);*/
    contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
  }

}
