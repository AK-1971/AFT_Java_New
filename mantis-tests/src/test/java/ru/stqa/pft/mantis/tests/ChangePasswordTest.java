package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTest extends TestBase {

  @BeforeMethod //реализация на внутреннем почтовом сервере
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testUpdatePassword() throws MessagingException, IOException {
    Users users = app.db().users();
    UserData[] usersArray = new UserData[users.size()];
    users.toArray(usersArray);
    UserData userForEdit = usersArray[1];
    //UserData userForEdit = users.iterator().next();

    int userId = userForEdit.getId();
    String user = userForEdit.getUsername();
    String email = userForEdit.getEmail();
    String newPassword = "newPassword";
    app.registration().loginAdmin("administrator", "root");
    app.registration().resetUserPassword(userId);
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finishResetPassword(confirmationLink, newPassword);
    assertTrue(app.newSession().login(user, newPassword));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}
