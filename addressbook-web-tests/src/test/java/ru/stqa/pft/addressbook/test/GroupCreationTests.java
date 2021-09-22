package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData group = new GroupData().setName("test1").setHeader("header1").setFooter("footer1");
    app.group().create(group);
    Set<GroupData> after = app.group().all();
    //Assert.assertEquals(after.size(), before.size() + 1);
    assertThat(after.size(), equalTo(before.size() + 1));

    /*int maxID = 0;              //Вместо этого блока можно использовать lambda функцию
    for(GroupData g : after) {
      if(maxID < g.getId()) {
         maxID = g.getId();
      }
    }
    group.setId(maxID);*/
    //group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    group.setId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt());
    before.add(group);
    //Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
    assertThat(after, equalTo(before));
  }

}
