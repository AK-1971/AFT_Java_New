package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.thoughtworks.xstream.XStream;
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

  @Parameter(names = "-d", description = "Data format") //6.3.
  public String format;


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
    if (format.equals("csv")) {
      saveAsCsv(groups, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(groups, new File(file));
    } else {
      System.out.println("Unrecognazed format " + format);
    }
  }

  private void saveAsXml(List<GroupData> groups, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.alias("group", GroupData.class);
    //xstream.processAnnotations(GroupData.class); если делать так (как у АБ в 6.6.), то тогда в GorupData надо менять аннотации
    xstream.omitField(GroupData.class, "id");
    String xml = xstream.toXML(groups);
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
  }

  private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    Writer writer = new FileWriter(file);
    for (GroupData group : groups) {
      writer.write(String.format("%s;%s;%s\n", group.getGroupName(), group.getHeader(), group.getFooter()));
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
