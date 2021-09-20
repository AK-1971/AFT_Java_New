package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isContactPresent()) {
      app.getContactHelper().create(new ContactData("Ivan", "Ivanovich", "Ivanov",
              "Beetle", "NCC", "Moscow", "123456789",
              "asdf@mail.ru", "bla bla", "test1"));
      app.getNavigationHelper().gotoHomePage();
    }
    app.getContactHelper().initEditContact();
    app.getContactHelper().fillContact(new ContactData("Contact", null, "Modified",
            null, null, null, null, null, null, null), false);
    app.getContactHelper().updateContactEdit();
    app.getNavigationHelper().gotoHomePage();
  }
}
