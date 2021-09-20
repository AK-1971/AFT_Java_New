package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().initNewContact();
    app.getContactHelper().fillContact(new ContactData("Ivan", "Ivanovich", "Ivanov",
            "Beetle", "NCC", "Moscow", "123456789",
            "asdf@mail.ru", "bla bla", "test1"), true);
    app.getContactHelper().submitContactForm();
    app.getNavigationHelper().gotoHomePage();
  }

}
