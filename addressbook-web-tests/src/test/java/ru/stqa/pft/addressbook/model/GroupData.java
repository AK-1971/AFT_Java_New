package ru.stqa.pft.addressbook.model;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) { //Этот метод "обьясняет Java как сравнивать объекты типа GroupData
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GroupData groupData = (GroupData) o;
    return Objects.equals(group_name, groupData.group_name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(group_name);
  }

  @Override
  public String toString() { //Данный метод позволяет выводить в сообщениях на консоли инфо об объекте
    return "GroupData{" +    //подробнее в 4.6. на 4.00
            "group_name='" + group_name + '\'' +
            '}';
  }
}
