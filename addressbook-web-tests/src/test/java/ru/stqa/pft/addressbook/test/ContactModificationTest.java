package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void preconditions(){
    app.contact().createIfNotExists();
  }

  @Test
  public void testContactModification() {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData modifyingContact = new ContactData().setID((before.get(index)).getId()) //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            .setFirstname("Contact").setLastname("Modified").setNickname("Nick").setCompany("MMM")
            .setAddress("AddressM").setHome("Home").setEmail("email");
    app.contact().modify(index, modifyingContact);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(modifyingContact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));

  }


}
