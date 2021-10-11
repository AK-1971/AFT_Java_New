package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static ru.stqa.pft.mantis.tests.TestBase.app;

public class RegistrationTestsJames {

  //перед запуском теста необходимо запустить почтовый сервер James в ком. строке  в директории C:\Tools\james-2.3.1\bin
  // следующие 3 команды 1) set JAVA_HOME=C:\Program Files\Zulu\zulu-8  2) set PATH=%JAVA_HOME%\bin;%PATH%  3) run.bat

  @Test
  public void testRegistration() throws MessagingException, IOException {
    long now = System.currentTimeMillis();
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user%s@localhost", now);
    //String email = String.format("user%s@localhost.localdomain", now);
    app.james().createUser(user, password); //создание юзера на внешнем сервере
    app.registration().start(user, email);
    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);//получение письма на внешнем сервере
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    Assert.assertTrue(app.newSession().login(user, password));
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) ->
            m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace()
            .oneOrMore().build();
    return regex.getText(mailMessage.text);
  }


}
