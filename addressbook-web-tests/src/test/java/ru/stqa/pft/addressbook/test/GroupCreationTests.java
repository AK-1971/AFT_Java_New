package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    int before = app.getGroupHelper().getCount();
    app.getGroupHelper().create(new GroupData("test1", "header1", "footer1"));
    int after = app.getGroupHelper().getCount();
    Assert.assertEquals(after, before + 1);
  }

}
