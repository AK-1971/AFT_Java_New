package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTest extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isContactPresent()) {
      app.getContactHelper().create(new ContactData("Ivan", "Ivanovich", "Ivanov",
              "Beetle", "NCC", "Moscow", "123456789",
              "asdf@mail.ru", "bla bla", "test1"));
      app.getNavigationHelper().gotoHomePage();
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteContactAndCofirm();
  }

}
