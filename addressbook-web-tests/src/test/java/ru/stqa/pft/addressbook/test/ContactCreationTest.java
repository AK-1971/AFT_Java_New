package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.Set;

public class ContactCreationTest extends TestBase {

  String groupName = new String(); //не создавал конструктора без группы, поэтому имя группы в контакт передавать нужно

  @BeforeMethod
  public void preconditions() {
    app.group().createIfNotExists();
    app.goTo().groupPage();
    /*List<GroupData> group = app.group().list();//выясняем имя группы в списке (берем первую)
    groupName = group.get(0).getGroupName(); //и передаем его в данные контакта*/
    Set<GroupData> group = app.group().all();
    groupName = group.iterator().next().getGroupName();
  }

  @Test(enabled = true)
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/nafan.jpg");
    ContactData contact = new ContactData().setFirstname("Ivan").setMiddlename("Ivanovich").setLastname("Ivanov")
            .setNickname("Beetle").setCompany("NCC").setAddress("Moscow").setHomePhone("123456789")
            .setAllEmail("vano@mail.ru").setNotes("bla bla").setPhoto(photo)
            .setGroup(String.format("%s", groupName));
    app.contact().create(contact);
    app.goTo().homePage();
    Assert.assertEquals(app.contact().getCount(), before.size() + 1);
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
