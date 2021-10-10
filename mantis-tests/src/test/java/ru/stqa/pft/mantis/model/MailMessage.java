package ru.stqa.pft.mantis.model;

public class MailMessage { //8.6. - объяснение на 10.30
  public String to;
  public String text;

  public MailMessage(String to, String text) {
    this.to = to;
    this.text = text;
  }
}
