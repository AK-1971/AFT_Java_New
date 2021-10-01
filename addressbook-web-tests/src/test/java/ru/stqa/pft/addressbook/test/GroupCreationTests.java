package ru.stqa.pft.addressbook.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GroupCreationTests extends TestBase {

  Logger logger = LoggerFactory.getLogger(GroupCreationTests.class);

  @DataProvider
  public Iterator<Object[]> groupsCSV() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader
            (new FileReader(new File("src/test/resources/groups.csv"))); //6.5.
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split("; "); // тот символ что и в генераторе, если %s будут разделяться запятыми, то и здесь запятые
      list.add(new Object[] {new GroupData().setName(split[0]).setHeader(split[1]).setFooter(split[2])});
      line = reader.readLine();
    }
    return list.iterator();
  }

  @DataProvider
  public Iterator<Object[]> groupsXML() throws IOException { //6.6.
    BufferedReader reader = new BufferedReader
            (new FileReader(new File("src/test/resources/groups.xml")));
    String xml = "";
    String line = reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.alias("group", GroupData.class); // не стал обрабатывать аннотации в классе GroupData
    xstream.omitField(GroupData.class, "id"); // как у Алексея, чтобы не загромождать
    List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> groupsJson() throws IOException { //6.7.
    BufferedReader reader = new BufferedReader
            (new FileReader(new File("src/test/resources/groups.json")));
    String json = "";
    String line = reader.readLine();
    while (line != null) {
      json += line;
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>(){}.getType()); //см 6.7. 08.40
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }


  @Test(dataProvider = "groupsJson")
  public void testGroupCreation(GroupData group) throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all(); //5.6. меняем также тип метода all
    app.group().create(group);
    assertThat(app.group().getCount(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    assertThat(after, equalTo(
            before.withAdded(group.setId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
  }

}
