package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactFeateresTestNew extends TestBase{

  @Test(enabled = false)
  public void testPhonesEmails_5_10() { //тест работает только если заполнены все три поля в телефонах и мейлах
    ContactData contact = app.contact().all_Lesson5_10().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getHomePhone(), equalTo(cleanPhone(contactInfoFromEditForm.getHomePhone())));
    assertThat(contact.getMobilePhone(), equalTo(cleanPhone(contactInfoFromEditForm.getMobilePhone())));
    assertThat(contact.getWorkPhone(), equalTo(cleanPhone(contactInfoFromEditForm.getWorkPhone())));

    assertThat(contact.getEmail(), equalTo(cleanEmail(contactInfoFromEditForm.getEmail())));
    assertThat(contact.getEmail2(), equalTo(cleanEmail(contactInfoFromEditForm.getEmail2())));
    assertThat(contact.getEmail3(), equalTo(cleanEmail(contactInfoFromEditForm.getEmail3())));
  }

  public static String cleanPhone(String phone){
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "")
            .replaceAll("\\'", "");
  }

  public static String cleanEmail(String email){
    return email.replaceAll("\'", "").replaceAll("\"", "")
            .replaceAll("\\s+(?![^\\d\\s])", ""); //последнее выражение - если будет больше 1го пробела подряд
  }
}
