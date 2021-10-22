package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

public class RestTest {

  @Test
  public void testCreateIssue() throws IOException {
    long now = System.currentTimeMillis();
    String subject =  String.format("AK Test issue_%s", now);
    String description =  String.format("Test description_%s", now);
    Set<Issue> oldIssues = getIssue();
    Issue newIssue = new Issue().setSubject(subject).setDescription(description);
    int issueID = createIssue(newIssue);
    Set<Issue> newIssues = getIssue();
    oldIssues.add(newIssue.setId(issueID));
    int i = 0;
    for (Issue issue : newIssues) {
      i++;
      System.out.println(" " + i + ". " + "Issue ID: " + issue.getId() + "  Subject: " + issue.getSubject()
              + "  Description: " + issue.getDescription());
    }
    Assert.assertEquals(newIssues, oldIssues);
  }

  private Set<Issue> getIssue() throws IOException {
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
  }

  private int createIssue(Issue newIssue) throws IOException {
    String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  private Executor getExecutor() {
    return Executor.newInstance()
            .auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }


}
