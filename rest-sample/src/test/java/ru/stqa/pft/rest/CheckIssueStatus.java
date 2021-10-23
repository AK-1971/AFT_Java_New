package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

public class CheckIssueStatus extends TestBase {

  @Test
  public void isIssueReadyTotest() throws IOException {
    //В общем списке Issues выберу только свои - там где subject начинается на АК
    Set<Issue> listIssues = getIssue();

    for (Issue issue : listIssues) {

      String str = issue.getSubject();
      char[] issueSubject = new char[str.length()];

      for (int i = 0; i < str.length(); i++) {

        issueSubject[i] = str.charAt(i);
        char condition = 'A';
        if (i != 0 ) condition = 'K';
        if (issueSubject[i] != condition) break;
        if ( i == 1 )  skipIfNotFixed(issue.getId());

      }
    }

  }
  private Set<Issue> getIssue() throws IOException {
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
  }

}
