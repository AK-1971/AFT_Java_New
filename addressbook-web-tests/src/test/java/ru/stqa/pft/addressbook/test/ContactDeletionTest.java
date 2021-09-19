package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;

public class ContactDeletionTest extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    //wd.get("http://localhost/addressbook/");
    app.getContactHelper().selectContact();
    //acceptNextAlert = true;
    app.getContactHelper().confirmDeletionContact();
    //assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
    //acceptNextAlert = true;
  }

}
