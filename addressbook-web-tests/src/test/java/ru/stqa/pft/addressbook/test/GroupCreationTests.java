package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new GroupData().setName("test_1").setHeader("header_1").setFooter("footer_1")});
    list.add(new Object[] {new GroupData().setName("test_2").setHeader("header_2").setFooter("footer_2")});
    list.add(new Object[] {new GroupData().setName("test_3").setHeader("header_3").setFooter("footer_3")});
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all(); //5.6. меняем также тип метода all
    //GroupData group = new GroupData().setName("test1").setHeader("header1").setFooter("footer1");
    app.group().create(group);
    assertThat(app.group().getCount(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    //group.setId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt());
    assertThat(after, equalTo(
            before.withAdded(group.setId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
  }

}
