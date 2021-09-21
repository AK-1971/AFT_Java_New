package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroup() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getGroup_name());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroup() {
    click(By.xpath("//input[5]"));
  }

  public void selectGroup(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroup();
    returnToGroupPage();

  }

  public boolean isThereGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<GroupData> getGroupList() { // 4.4.
    List<GroupData> groups = new ArrayList<GroupData>(); //создаем список групп на странице
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group")); //определяем что ищем (в нашем случае получим только имя)
    for (WebElement element : elements) { //заполняем присутствующими элементами
      String name = element.getText(); //получаем имя (хедер и футер получить не можем
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value")); //4.7.на 08.00 поиск одного элемента внутри другого
      GroupData group = new GroupData(id, name, null, null); //создаем объект из полученного
      groups.add(group); // добавляем в список
    }
    return groups;
  }
}
