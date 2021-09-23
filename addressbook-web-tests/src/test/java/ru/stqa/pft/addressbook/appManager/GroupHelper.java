package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupHelper extends HelperBase {

  AppsManager manager;

  public GroupHelper(AppsManager manager) {
    super(manager.wd);
    this.manager = manager;
  }
  /*public GroupHelper(WebDriver wd) {
    super(wd);
  }*/

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroup() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getGroupName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroup() {
    click(By.xpath("//input[5]"));
  }

  public void selectGroup(int index) {  //извлекает группу из упорядоченного списка по ее номеру
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  private void selectGroupById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
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

  public void modify(GroupData modifyingGroup) {
    selectGroupById(modifyingGroup.getId());
    initGroupModification();
    fillGroupForm(modifyingGroup);
    submitGroupModification();
    returnToGroupPage();
  }

  public void delete(int groupFromList) { //этот метод работал с упорядоченными множествами - до 5.5.
    selectGroup(groupFromList);
    deleteSelectedGroup();
    returnToGroupPage();
  }

  public void delete(GroupData deletedGroup) { //5.5. - метод удаляет объект из неупорядоченного множества
    selectGroupById(deletedGroup.getId());
    deleteSelectedGroup();
    returnToGroupPage();
  }

  public void createIfNotExists() {
    manager.goTo().groupPage();
    if (!isThereGroup()) {
     create(new GroupData().setName("test1").setHeader("test2").setFooter("test3"));
    }
  }


  public boolean isThereGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  /*public List<GroupData> list() { // 4.4.
    List<GroupData> groups = new ArrayList<GroupData>(); //создаем список групп на странице
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group")); //определяем что ищем (в нашем случае получим только имя)
    for (WebElement element : elements) { //заполняем присутствующими элементами
      String name = element.getText(); //получаем имя (хедер и футер получить не можем
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value")); //4.7.на 08.00 поиск одного элемента внутри другого
      GroupData group = new GroupData().setId(id).setName(name); //создаем объект из полученного
      groups.add(group); // добавляем в список
    }
    return groups;
  }*/

  /*public Set<GroupData> all() { // 5.5. вместо метода list
    Set<GroupData> groups = new HashSet<GroupData>(); //создаем список групп на странице
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group")); //определяем что ищем (в нашем случае получим только имя)
    for (WebElement element : elements) { //заполняем присутствующими элементами
      String name = element.getText(); //получаем имя (хедер и футер получить не можем
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value")); //4.7.на 08.00 поиск одного элемента внутри другого
      GroupData group = new GroupData().setId(id).setName(name); //создаем объект из полученного
      groups.add(group); // добавляем в список
    }
    return groups;
  }*/

  public Groups all() { // 5.6.
    Groups groups = new Groups(); //создаем список групп на странице
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group")); //определяем что ищем (в нашем случае получим только имя)
    for (WebElement element : elements) { //заполняем присутствующими элементами
      String name = element.getText(); //получаем имя (хедер и футер получить не можем
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value")); //4.7.на 08.00 поиск одного элемента внутри другого
      GroupData group = new GroupData().setId(id).setName(name); //создаем объект из полученного
      groups.add(group); // добавляем в список
    }
    return groups;
  }


}
