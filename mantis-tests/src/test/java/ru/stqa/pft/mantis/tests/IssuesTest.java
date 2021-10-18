package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class IssuesTest extends TestBase {

  @Test
  public void testShowStatus() throws MalformedURLException, ServiceException, RemoteException {

    BigInteger issueId = BigInteger.valueOf(0000002);
    skipIfNotFixed(issueId);

  }
}
