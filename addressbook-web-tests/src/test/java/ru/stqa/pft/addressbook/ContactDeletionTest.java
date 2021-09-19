package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class ContactDeletionTest extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    //wd.get("http://localhost/addressbook/");
    selectContact();
    //acceptNextAlert = true;
    confirmDeletionContact();
    //assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
    //acceptNextAlert = true;
  }

}
