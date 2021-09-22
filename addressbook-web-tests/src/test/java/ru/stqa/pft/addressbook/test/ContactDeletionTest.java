package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTest extends TestBase {

  @BeforeMethod
  public void preconditions(){
    app.contact().createIfNotExists();
  }


  @Test
  public void testContactDeletion() throws Exception {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    app.contact().delete(index);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), index);
    before.remove(index);
    for (int i = 0; i < after.size(); i++) { //можно таким способом сравнивать каждый член коллекции
      Assert.assertEquals(before.get(i), after.get(i));
    }
    Assert.assertEquals(before, after); //тут фреймворк сам организовывает такой цикл
  }


}
