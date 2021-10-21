package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.IssueInfo;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTest extends TestBase{

  long now = System.currentTimeMillis();

  @Test(enabled = false)
  public void testGetProjects2() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();
    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }

  @Test(enabled = true)
  public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();
    IssueInfo issue = new IssueInfo().setSummary(String.format("Test issue_%s", now)).setDescription("Test issue description")
            .setProject(projects.iterator().next());
    IssueInfo created = app.soap().addIssue(issue);
    Assert.assertEquals(issue.getSummary(), created.getSummary());
  }

    //Первоначальный тест
  @Test(enabled = true)
  public void testGetProjects1() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = new MantisConnectLocator()
            .getMantisConnectPort(new URL("http://localhost/mantisbt-1.2.20/api/soap/mantisconnect.php"));
    ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
    System.out.println(projects.length);
    for (ProjectData project : projects) {
      System.out.println(project.getName());
    }
  }
}
