package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

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
    Contacts before = app.contact().all(); //5.6.
    ContactData oldContact = before.iterator().next();
    ContactData newContact = new ContactData().setID((oldContact).getId()) //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            .setFirstname("Contact").setLastname("Modified").setNickname("Nick").setCompany("MMM")
            .setAddress("AddressM").setHome("Home").setEmail("email");
    app.contact().modify(newContact);
    app.goTo().homePage();
    Assert.assertEquals(app.contact().getCount(), before.size());
    Contacts after = app.contact().all();
    /*before.remove(oldContact); этот модуль работал до 5.6.
    before.add(newContact);
    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));*/
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.withOut(oldContact).withAdded(newContact)));

  }


}
