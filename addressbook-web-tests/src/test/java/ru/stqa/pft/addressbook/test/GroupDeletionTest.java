package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTest extends TestBase {

  @BeforeMethod
  public void preconditions() {
    app.getGroupHelper().createGroupIfNotExists();
  }

  @Test
  public void testGroupDeletion() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().deleteSelectedGroup();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(before.size() - 1);
    for (int i = 0; i < after.size(); i++) { //можно таким способом сравнивать каждый член коллекции
      Assert.assertEquals(before.get(i), after.get(i));
    }
    Assert.assertEquals(before, after); //тут фреймворк сам организовывает такой цикл


  }


}
