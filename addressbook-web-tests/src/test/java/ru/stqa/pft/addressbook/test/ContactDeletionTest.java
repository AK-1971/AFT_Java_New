package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;
import java.util.Set;

public class ContactDeletionTest extends TestBase {

  @BeforeMethod
  public void preconditions(){
    app.contact().createIfNotExists();
  }


  @Test
  public void testContactDeletion() throws Exception {
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(deletedContact);
    /*for (int i = 0; i < after.size(); i++) { //можно таким способом сравнивать каждый член упорядоченной коллекции
      Assert.assertEquals(before.get(i), after.get(i)); так работал до того как list() поменял аll()
    }*/
    Assert.assertEquals(before, after); //тут фреймворк сам организовывает такой цикл
  }


}
