package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase{

  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().modifyContact();
    app.getContactHelper().fillContact(new ContactData("NameM", "MdlMod", "LastMode",
            "NickMod", "comMod", "AdrMod", "mod123", "mailMod", "hz"));
    app.getContactHelper().updateContactEdit();
    app.getNavigationHelper().gotoHomePage();
  }
}
