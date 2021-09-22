package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;
import java.util.Set;

public class GroupDeletionTest extends TestBase {

  @BeforeMethod
  public void preconditions() {
    app.group().createIfNotExists();
  }

  @Test
  public void testGroupDeletion() throws Exception {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(deletedGroup);
    /*for (int i = 0; i < after.size(); i++) { //можно таким способом сравнивать каждый член коллекции
      Assert.assertEquals(before.get(i), after.get(i)); // если эта коллекция List как было до 5.5. когда вместо метода all()
    } был метод list()*/
    Assert.assertEquals(before, after); //тут фреймворк сам организовывает такой цикл
  }

}
