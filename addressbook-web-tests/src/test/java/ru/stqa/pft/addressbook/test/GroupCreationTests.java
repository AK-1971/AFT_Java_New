package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all(); //5.6. меняем также тип метода all
    GroupData group = new GroupData().setName("test1").setHeader("header1").setFooter("footer1");
    app.group().create(group);
    assertThat(app.group().getCount(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    //group.setId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt());
    assertThat(after, equalTo(
            before.withAdded(group.setId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
  }

}
