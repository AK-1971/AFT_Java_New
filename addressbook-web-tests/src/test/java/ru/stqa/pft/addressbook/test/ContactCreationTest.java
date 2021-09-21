package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTest extends TestBase {

 /* @BeforeMethod
  public void preconditions(){ //поскольку указано обязательное добавление в группу м именем test1, создаем
    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().create(new GroupData("test1", "header1", "footer1"));
  }*/

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    int before = app.getContactHelper().getCount();
    app.getContactHelper().create(new ContactData("Ivan", "Ivanovich", "Ivanov",
            "Beetle", "NCC", "Moscow", "123456789",
            "asdf@mail.ru", "bla bla", "test1"));
    app.getNavigationHelper().gotoHomePage();
    int after = app.getContactHelper().getCount();
    Assert.assertEquals(after, before + 1);
  }

}
