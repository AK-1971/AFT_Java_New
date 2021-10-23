package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class TestBase {
  public boolean isIssueOpen(int issueId) throws IOException {
    String json = getExecutor()
            .execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json", issueId)))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    System.out.println("Issue " +  issueId );
    System.out.println(parsed.getAsJsonObject().get("state_name").getAsString());
    return parsed.getAsJsonObject().get("state_name").getAsString().equals("Open");
    //return parsed.getAsJsonObject().get("state").getAsInt() == 0;
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (!isIssueOpen(issueId)) {
      System.out.println("Issue " + issueId + " not fixed");
      throw new SkipException("Ignored because of issue " + issueId);
    }
    System.out.println("Issue " + issueId + " is ready for tests");  }

  public Executor getExecutor() {
    return Executor.newInstance()
            .auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

}
