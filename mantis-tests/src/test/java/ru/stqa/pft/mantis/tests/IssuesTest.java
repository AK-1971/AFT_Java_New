package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.IssueInfo;
import ru.stqa.pft.mantis.model.Issues;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class IssuesTest extends TestBase {

  @Test
  public void testShowStatus() throws MalformedURLException, ServiceException, RemoteException {

    Issues issues = app.db().issues();
    IssueInfo[] issuesArray = new IssueInfo[issues.size()];
    issues.toArray(issuesArray);

    /*BigInteger issueId;
    for (int i = 0; i < issues.size(); i++) {
      issueId = BigInteger.valueOf(issuesArray[i].getId());
      //issueId = BigInteger.valueOf(issuesArray[i].getId());
      skipIfNotFixed(issueId);
    }*/
  }
}
