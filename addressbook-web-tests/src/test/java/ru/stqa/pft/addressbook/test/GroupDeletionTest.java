package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTest extends TestBase {

  @BeforeMethod
  public void preconditions() {
    app.group().createIfNotExists();
  }

  @Test
  public void testGroupDeletion() throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all(); //5.6.
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    Assert.assertEquals(app.group().getCount(), before.size() - 1);
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withOut(deletedGroup)));
  }

}
