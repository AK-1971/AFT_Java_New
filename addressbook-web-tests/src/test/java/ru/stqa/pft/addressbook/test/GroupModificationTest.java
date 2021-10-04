package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTest extends TestBase {

  @BeforeMethod
  public void preconditions() {
    app.group().createIfNotExists();
  }

  @Test(enabled = true)
  public void testGroupModificationDB() {
    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .setId(modifiedGroup.getId()).setName("test1Mod").setHeader( "test2Mod")
            .setFooter("test3Mod");
    app.goTo().groupPage();
    app.group().modify(group);

    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.withOut(modifiedGroup).withAdded(group)));
  }

  @Test(enabled = false)
  public void testGroupModificationWeb() {//этот тест упадет, т.к. сейчас перегенерировал сравнение по всем полям, в web они не все отображены, а в базе - да
    app.goTo().groupPage();
    Groups before = app.group().all(); //5.6.
    GroupData oldGroup = before.iterator().next();

    GroupData newGroup = new GroupData().setId(oldGroup.getId()) //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            .setName("testModifyed").setHeader("HeaderModifyed").setFooter("FooterModifyed");
    app.group().modify(newGroup);
    Assert.assertEquals(app.group().count(), before.size());
    Groups after = app.group().all();
    /*before.remove(oldGroup);
    before.add(newGroup);
    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));*/
    assertThat(after, equalTo(before.withOut(oldGroup).withAdded(newGroup)));
  }

}
