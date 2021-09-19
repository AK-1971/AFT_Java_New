package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    initNewContact();
    fillContact(new ContactData("Ivan", "Ivanovich", "Ivanov",
            "Beetle", "NCC", "Moscow", "123456789",
            "asdf@mail.ru", "bla bla"));
    submitContactForm();
    gotoHomePage();
  }

}
