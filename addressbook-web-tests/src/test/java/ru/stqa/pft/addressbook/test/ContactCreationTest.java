package ru.stqa.pft.addressbook.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ContactCreationTest extends TestBase {

  //String groupName = new String(); //не создавал конструктора без группы, поэтому имя группы в контакт передавать нужно

  @DataProvider
  public Iterator<Object[]> contactsCSV() throws IOException {
    app.goTo().groupPage(); //создаем список из которого берем группу в которую
    // с помощью setGroup(group.getId() добавим контакт
    app.group().createIfNotExists(); //поскольку провайдер выполняется до before метода, проверяем наличие группы
    app.goTo().groupPage();
    List<GroupData> groups = app.group().list();
    GroupData group = groups.get(0);
    File photo = new File("src/test/resources/nafan.jpg");
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader
            (new FileReader(new File("src/test/resources/contacts.csv"))); //6.5.
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";"); // тот символ что и в генераторе, если %s будут разделяться запятыми, то и здесь запятые
      list.add(new Object[] {new ContactData().setFirstname(split[0]).setLastname(split[1]).setAllPhones(split[2])
              .setAddress(split[3]).setAllEmail(split[4]).setPhotoPath(photo.getPath()).setGroup(group.getId())});//.setPhoto(new File(split[5]))});
      line = reader.readLine();
    }
    return list.iterator();
  }

  @DataProvider
  public Iterator<Object[]> contactsXML() throws IOException { //6.6.
    BufferedReader reader = new BufferedReader
            (new FileReader(new File("src/test/resources/contacts.xml")));
    String xml = "";
    String line = reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.alias("contact", ContactData.class); // не стал обрабатывать аннотации в классе ContactData
    xstream.omitField(ContactData.class, "id"); // как у Алексея, чтобы не загромождать
    List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
    return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> contactsJSON() throws IOException {
    BufferedReader reader =
            new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
    String json = "";
    String line = reader.readLine();
    while (line != null) {
      json += line;
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
    
    //создаем список из которого берем группу в которую с помощью setGroup(group.getId() добавим контакт
    app.goTo().groupPage();
    List<GroupData> groups = app.group().list();
    GroupData group = groups.get(0);
    return contacts.stream().map((c) -> new Object[]{c.setGroup(group.getId())})
            .collect(Collectors.toList()).iterator();
  }

  @Test(dataProvider = "contactsJSON")
  public void testContactCreation(ContactData contactFromProvaider) throws Exception {
    app.goTo().groupPage(); //создаем список из которого берем группу в которую
    // с помощью setGroup(group.getId() добавим контакт
    List<GroupData> groups = app.group().list();
    GroupData group = groups.get(0);

    app.goTo().homePage();
    ContactData contact = contactFromProvaider.setGroup(group.getId());
    Contacts before = app.contact().all();
    app.contact().create(contact);
    app.goTo().homePage();
    Assert.assertEquals(app.contact().getCount(), before.size() + 1);
    app.goTo().homePage();
    Contacts after = app.contact().all();
    contact.setId(after.stream().mapToInt((c)-> c.getId()).max().getAsInt());
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.withAdded(contact)));
  }

  @Test(enabled = false)
  public void currentDirDefinition(){
    File currentDir = new File("."); //точка означает рабочую директорию проекта
    System.out.println(currentDir.getAbsolutePath()); //для ее определения воспользуемся методом getAbsolutePath, по которому определим какая директория проекта - рабочая
    File photo = new File("src/test/resources/nafan.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }

}
