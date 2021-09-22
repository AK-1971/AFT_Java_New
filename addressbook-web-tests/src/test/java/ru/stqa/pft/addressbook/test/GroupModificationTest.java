package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

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
    Set<GroupData> before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();

    GroupData modifyingGroup = new GroupData().setId(modifiedGroup.getId()) //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            .setName("testModifyed").setHeader("HeaderModifyed").setFooter("FooterModifyed");
    app.group().modify(modifyingGroup);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedGroup);
    before.add(modifyingGroup);

    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
  }

}
