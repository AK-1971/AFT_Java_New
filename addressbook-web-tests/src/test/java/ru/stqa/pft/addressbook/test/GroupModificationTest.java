package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupModificationTest extends TestBase {

  @BeforeMethod
  public void preconditions() {
    app.group().createIfNotExists();
  }

  @Test
  public void testGroupModification() {
    app.goTo().groupPage();
    List<GroupData> before = app.group().list();
    int index = before.size() - 1;
    GroupData modifyingGroup = new GroupData().setId((before.get(index)).getId()) //4.7. на 11.00 сохраняем индекс модифицируемого элемента
            .setName("testModifyed").setHeader("HeaderModifyed").setFooter("FooterModifyed");
    app.group().modify(index, modifyingGroup);
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(modifyingGroup);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
  }

}
