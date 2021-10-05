package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void preconditions(){
    app.contact().createIfNotExists();
  }

  @Test
  public void testContactModificationDB() {
    Contacts before = app.db().contacts(); //7.5.
    ContactData oldContact = before.iterator().next();
    ContactData newContact = new ContactData().setId((oldContact).getId()) //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            .setFirstname("Contact").setLastname("Modified").setNickname("Nick").setCompany("MMM")
            .setAddress("AddressM").setMobilePhone("Mobile").setEmail("email");
    app.contact().modify(newContact);
    app.goTo().homePage();
    Assert.assertEquals(app.contact().getCount(), before.size());
    Contacts after = app.db().contacts();
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.withOut(oldContact).withAdded(newContact)));
  }

  @Test(enabled = false)
  public void testContactModificationWeb() {//тест упадет если сгенерировано полей для сравнения больше чем показано на странице
    Contacts before = app.contact().all(); //5.6.
    ContactData oldContact = before.iterator().next();
    ContactData newContact = new ContactData().setId((oldContact).getId()) //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            .setFirstname("Contact").setLastname("Modified").setNickname("Nick").setCompany("MMM")
            .setAddress("AddressM").setHomePhone("Home").setAllEmail("email");
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
