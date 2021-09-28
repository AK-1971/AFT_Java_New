package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator { //создание см. 6.2.

  @Parameter(names = "-c", description = "Group count") //6.3.
  public int count;

  @Parameter(names = "-f", description = "Target file") //6.3.
  public String file;

  public static void main(String[] args) throws IOException {
    GroupDataGenerator generator = new GroupDataGenerator(); //6.3.
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (Exception ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException { //6.3.
    List<GroupData> groups = generateGroups(count);
    save(groups, new File(file));
  }

  private void save(List<GroupData> groups, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    Writer writer = new FileWriter(file);
    for (GroupData group : groups) {
      writer.write(String.format("%s; %s; %s\n", group.getGroupName(), group.getHeader(), group.getFooter()));
    }
    writer.close();
  }

  private List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().setName(String.format("test_%s", i)).setHeader(String.format("header_%s", i))
              .setFooter(String.format("footer_%s", i)));
    }
    return groups;
  }
}
