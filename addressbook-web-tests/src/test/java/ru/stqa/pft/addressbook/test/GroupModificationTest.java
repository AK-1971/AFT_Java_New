package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupModificationTest extends TestBase {

  @BeforeMethod
  public void preconditions() {
    app.group().createIfNotExists();
  }

  @Test
  public void testGroupModification() {
    app.goTo().groupPage();
    Groups before = app.group().all(); //5.6.
    GroupData oldGroup = before.iterator().next();

    GroupData newGroup = new GroupData().setId(oldGroup.getId()) //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            .setName("testModifyed").setHeader("HeaderModifyed").setFooter("FooterModifyed");
    app.group().modify(newGroup);
    Assert.assertEquals(app.group().getCount(), before.size());
    Groups after = app.group().all();
    /*before.remove(oldGroup);
    before.add(newGroup);
    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));*/
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.withOut(oldGroup).withAdded(newGroup)));
  }

}
