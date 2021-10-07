package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactFeateresTest extends TestBase{

  @BeforeMethod
  public void precinditions() {
    app.contact().createIfNotExists();
  }

  @Test(enabled = false)
  public void testContactPhones() { //5.10 - 5.11
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact, equalTo(contactInfoFromEditForm));
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));

    /*assertThat(contact.getEmail(), equalTo(cleanEmail(contactInfoFromEditForm.getEmail()))); так сравнение было в 5.10
    assertThat(contact.getEmail2(), equalTo(cleanEmail(contactInfoFromEditForm.getEmail2())));*/
    assertThat(contact.getAllEmail(), equalTo(mergeEmailes(contactInfoFromEditForm)));
  }

  private String mergeEmailes(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactFeateresTest::cleanEmail).collect(Collectors.joining("\n"));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactFeateresTest::cleaned).collect(Collectors.joining("\n"));
  }


  public static String cleaned(String phone){
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "")
            .replaceAll("\\'", "");
  }

  public static String cleanEmail(String email){
    return email.replaceAll("\'", "").replaceAll("\"", "")
            .replaceAll("\\s+(?![^\\d\\s])", ""); //последнее выражение - если будет больше 1го пробела подряд
  }
}
