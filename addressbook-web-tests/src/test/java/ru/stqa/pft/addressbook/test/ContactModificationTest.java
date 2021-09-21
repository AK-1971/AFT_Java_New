package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

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
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData modifyingContact = new ContactData((before.get(before.size() - 1)).getId(), //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            "Contact", null, "Modified","Nick", "MMM",
            "AddressM", "Home", "email", null, null);
    app.getContactHelper().chooseContactEdit(before.size() - 1);
    app.getContactHelper().fillContact((modifyingContact), false);
    app.getContactHelper().updateContactEdit();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(modifyingContact);
    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));

  }
}
