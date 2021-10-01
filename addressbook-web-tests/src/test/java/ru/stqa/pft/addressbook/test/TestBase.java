package ru.stqa.pft.addressbook.test;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appManager.AppsManager;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final AppsManager app =
          new AppsManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

  public AppsManager getApp() {
    return app;
  }

  @BeforeMethod
  public void logTestStart(Method method, Object[] p){
    logger.info("Start testGroupModification " + method.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestFinish(Method method, Object[] p) {
    logger.info("Finish testGroupModification" + method.getName() + " with parameters " + Arrays.asList(p));
  }

}
