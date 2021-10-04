package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;
import java.util.Set;

public class ContactDeletionTest extends TestBase {

  @BeforeMethod
  public void preconditions(){
    app.contact().createIfNotExists();
  }


  @Test
  public void testContactDeletion() throws Exception {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    Assert.assertEquals(app.contact().getCount(), before.size() - 1);
    Contacts after = app.db().contacts();
    //before.remove(deletedContact);
    /*for (int i = 0; i < after.size(); i++) { //можно таким способом сравнивать каждый член упорядоченной коллекции
      Assert.assertEquals(before.get(i), after.get(i)); так работал до того как list() поменял аll()
    }*/
    //Assert.assertEquals(before, after); //тут фреймворк сам организовывает такой цикл
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.withOut(deletedContact)));
  }


}
