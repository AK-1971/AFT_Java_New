package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void preconditions(){
    app.contact().createIfNotExists();
  }

  @Test
  public void testContactModification() {
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData modifyingContact = new ContactData().setID((modifiedContact).getId()) //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            .setFirstname("Contact").setLastname("Modified").setNickname("Nick").setCompany("MMM")
            .setAddress("AddressM").setHome("Home").setEmail("email");
    app.contact().modify(modifyingContact);
    app.goTo().homePage();
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(modifyingContact);
    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));

  }


}
