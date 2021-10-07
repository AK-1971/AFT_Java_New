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

  @Test(enabled = true)
  public void testContactPhones() { //5.10 - 5.11
    app.goTo().homePage();
    ContactData contact = app.contact().all_5_11().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAllEmail(), equalTo(mergeEmailes(contactInfoFromEditForm)));
    assertThat(contact, equalTo(contactInfoFromEditForm));
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
