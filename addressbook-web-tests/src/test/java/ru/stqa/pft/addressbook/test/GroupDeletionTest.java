package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTest extends TestBase {

  @BeforeMethod
  public void preconditions() {
    app.group().createIfNotExists();
  }

  @Test
  public void testGroupDeletionDB() throws Exception {
    app.goTo().groupPage();
    Groups before = app.db().groups(); //5.6.
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    Assert.assertEquals(app.group().count(), before.size() - 1);
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.withOut(deletedGroup)));
  }

  @Test(enabled = false)
  public void testGroupDeletionWeb() throws Exception {//этот тест упадет, т.к. сейчас перегенерировал сравнение по всем полям, в web они не все отображены, а в базе - да
    app.goTo().groupPage();
    Groups before = app.group().all(); //5.6.
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    Assert.assertEquals(app.group().count(), before.size() - 1);
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withOut(deletedGroup)));
  }

}
