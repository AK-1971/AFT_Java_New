package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.AppsManager;
import ru.stqa.pft.mantis.model.IssueInfo;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBase {

  protected static final AppsManager app =
          new AppsManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php",
            "config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }


  public boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    IssueInfo issue = app.soap().getIssue(issueId);

    System.out.println("Issue " +  issueId + " " + issue.getResolution().getName());
    return issue.getResolution().getName().equals("fixed");
  }

  public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    if (!isIssueOpen(issueId)) {
      System.out.println("Issue " + issueId + " not fixed");
      throw new SkipException("Ignored because of issue " + issueId);
    }
    System.out.println("Issue " + issueId + " is ready for tests");
  }
}
