package ru.stqa.pft.addressbook;

public class GroupData {
  private final String group_name;
  private final String header;
  private final String footer;

  public GroupData(String group_name, String header, String footer) {
    this.group_name = group_name;
    this.header = header;
    this.footer = footer;
  }

  public String getGroup_name() {
    return group_name;
  }

  public String getHeader() {
    return header;
  }

  public String getFooter() {
    return footer;
  }
}
