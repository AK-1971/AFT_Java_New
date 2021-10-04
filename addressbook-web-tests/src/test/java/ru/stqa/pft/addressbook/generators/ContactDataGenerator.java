package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.*;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count") //6.3.
  public int count;

  @Parameter(names = "-f", description = "Target file") //6.3.
  public String file;

  @Parameter(names = "-d", description = "Data format") //6.6.
  public String format;


  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (Exception ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")) {
      saveAsCSV(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXML(contacts, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognazed format " + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(File.class, new MyFileSerializer()).create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)){  //try - обьяснение в 6.8.
    writer.write(json);
    }
  }

  public class MyFileSerializer implements JsonSerializer<File> { //6.7.
    public JsonElement serialize(File src, Type typeOfSrc, JsonSerializationContext context) {
      JsonObject obj = new JsonObject();
      obj.add("path", new JsonPrimitive(src.getPath()));
      return obj;
    }
  }

 /* private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }*/ //Вариант (!?!) без сериализатора - если делать так, то изменить CpntactData и СontactCreation

  private void saveAsXML(List<ContactData> contacts, File file) throws IOException {//6.6.
    XStream xstream = new XStream();
    xstream.alias("contact", ContactData.class);
    //xstream.processAnnotations(GroupData.class); если делать так (как у АБ в 6.6.), то тогда в GroupData надо менять аннотации
    xstream.omitField(ContactData.class, "id");
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)){
      writer.write(xml);
    }
  }

  private void saveAsCSV(List<ContactData> contacts, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) { //"%s;%s;%s;%s;%s\n" если делать с пробелами, то пробелы добавятся в имена
      writer.write(String.format("%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getLastname(),
              contact.getAllPhones(), contact.getAddress(), contact.getAllEmail()));
    }
    writer.close(); //если не использовать try - тогда надо обязательно закрывать writer
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    File photo = new File("src/test/resources/nafan.jpg");
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().setFirstname(String.format("FilesName_%s", i))
              .setLastname(String.format("LastName_%s", i)).setAllPhones(String.format("Phone_%s", i))
              .setAddress(String.format("adress_%s", i)).setAllEmail(String.format("email_%s", i))
              .setPhotoPath(photo.getPath()));
              //.setPhotoPath(photo)); вариант (!?!) когда в сеттере передается файл
    }
    return contacts;
  }
}
