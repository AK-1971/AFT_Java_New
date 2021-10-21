package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.IssueInfo;
import ru.stqa.pft.mantis.model.Issues;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class IssuesTest extends TestBase {

  @Test
  public void testShowStatusDB() throws MalformedURLException, ServiceException, RemoteException {

    Issues issues = app.db().issues();
    IssueInfo[] issuesArray = new IssueInfo[issues.size()];
    issues.toArray(issuesArray);

    BigInteger issueId;
    for (int i = 0; i < issues.size(); i++) {
      issueId = issuesArray[i].getId();
      //issueId = BigInteger.valueOf(issuesArray[i].getId());
      skipIfNotFixed(issueId);
    }
  }

  @Test
  public void testShowStatus() throws MalformedURLException, ServiceException, RemoteException {

   /* MantisConnectPortType mc = new MantisConnectLocator()
            .getMantisConnectPort(new URL("http://localhost/mantisbt-1.2.20/api/soap/mantisconnect.php"));
    IssueData[] issues = mc.mc_project_get_issues("administrator", "root", );
    System.out.println(issues.length);
    BigInteger issueId;
    for (IssueData issue : issues) {
      issueId = issue.getId();
      //issueId = BigInteger.valueOf(issuesArray[i].getId());
      skipIfNotFixed(issueId);
    }*/
  }

}
