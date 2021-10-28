package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AppsManager {

  private final Properties properties;
  private String browser;
  public WebDriver wd;
  private ContactHelper contactHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private SessionHelper sessionHelper;
  private DbHelper dbHelper;

  public AppsManager(String browser) {
    this.browser = browser;
    properties = new Properties(); // в 6.10. - объяснение этой строки
  }

  public GroupHelper group() {
    return groupHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public ContactHelper contact() {
    return contactHelper;
  }

  public DbHelper db() { return dbHelper; } //7.4.

  public void init() throws IOException {
    String target = System.getProperty("target", "local"); //6.10 использование данных из файла конфигурации
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));// 6.10. local

    /*if (browser.equals(BrowserType.FIREFOX)) {wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME)) {wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)) {wd = new InternetExplorerDriver();
    } else wd = new OperaDriver();*/

    dbHelper = new DbHelper();

    if("".equals(properties.getProperty("selenium.server"))) {
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
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      //capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "Win10")));
      wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
    }

    //ожидание появления поля если вдруг интернет медленный, объяснение в 3.8. на 11.30
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);//это мешает при проверках когда элемент должен отсутствовать - тест будет зеленый, но время будет затрачено
    wd.get(properties.getProperty("web.baseUrl")); //6.10
    groupHelper = new GroupHelper(this);
    contactHelper = new ContactHelper(this);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));//6.10.
  }

  public void stop() {
    wd.quit();
  }

}
