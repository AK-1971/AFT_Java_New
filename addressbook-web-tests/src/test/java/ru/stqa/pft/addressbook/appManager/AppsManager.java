package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.concurrent.TimeUnit;

public class AppsManager {

  private String browser;
  public WebDriver wd;
  private ContactHelper contactHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private SessionHelper sessionHelper;

  public AppsManager(String browser) {
    this.browser = browser;
  }

  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }

  public ContactHelper getContactHelper() {
    return contactHelper;
  }

  public void init() {
    /*if (browser.equals(BrowserType.FIREFOX)) {wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME)) {wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)) {wd = new InternetExplorerDriver();
    } else wd = new OperaDriver();*/
    switch (browser) {
      case BrowserType.FIREFOX:
        wd = new FirefoxDriver();
        break;
      case BrowserType.CHROME:
        wd = new ChromeDriver();
        break;
      case BrowserType.IE:
        wd = new InternetExplorerDriver();
        break;
      default:
        wd = new OperaDriver();
    }
    //ожидание появления поля если вдруг интернет медленный, объяснение в 3.8. на 11.30
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);//это мешает при проверках когда элемент должен отсутствовать - тест будет зеленый, но время будет затрачено
    groupHelper = new GroupHelper(this);
    contactHelper = new ContactHelper(this);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    sessionHelper.login("admin", "secret");
  }

  public void stop() {
    wd.quit();
  }

}
