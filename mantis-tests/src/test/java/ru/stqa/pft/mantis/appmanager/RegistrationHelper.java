package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegistrationHelper extends HelperBase{

  public RegistrationHelper(AppsManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[value='Signup']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[value='Update User']"));
  }

  public void loginAdmin(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Login']"));
  }

  public void resetUserPassword(int id) {
    //click(By.xpath("/html/body/div[2]/p/span[1]")); //manage users
    //wd.manage().timeouts().implicitlyWait(201, TimeUnit.SECONDS);
    click(By.xpath("/html/body/table[2]/tbody/tr/td[1]/a[7]")); // Manage
    click(By.xpath("/html/body/div[2]/p/span[1]/a	")); //Manage users
    click(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id)));
    //click(By.cssSelector("input[value='Reset Password']"));
    click(By.xpath("/html/body/div[4]/form[1]"));

  }

  public void finishResetPassword(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"),password);
    type(By.name("password_confirm"),password);
    //click(By.cssSelector("button[type='submit']"));
    click(By.xpath("/html/body/div[2]/form/table/tbody/tr[10]/td[2]/input"));
  }
}
