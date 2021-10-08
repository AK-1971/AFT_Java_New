package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;

public class RegistrationHelper {
  private final AppsManager app;
  private WebDriver wd;

  public RegistrationHelper(AppsManager app) {
    this.app = app;
    wd = app.getDriver(); //8.4. Ленивая инициализация
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");

  }
}
