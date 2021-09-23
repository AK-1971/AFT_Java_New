package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

public class Groups extends ForwardingSet<GroupData> { //5.6.

  private Set<GroupData> delegate; //создаем объект которому будут делегированы стандартные методы GroupData + наши новые

  public Groups(Groups groups) {
    this.delegate = new HashSet<GroupData>(groups.delegate);
  }

  public Groups() {
    this.delegate = new HashSet<GroupData>();
  }

  @Override
  protected Set<GroupData> delegate() {
    return delegate;
  }
  public Groups withAdded(GroupData group) { //в этом методе мы делаем копию delegate и добавляем в нее объекта (group)- это даст возможность работать и с новым объектом и старым, неизменённым множеством
    Groups groups = new Groups(this);//создаем копию - новое множество с помощью конструктора
    groups.add(group); //в эту копию добавляем объект
    return groups; //возвращаем эту копию с добавленной группой
  }

  public Groups withOut(GroupData group) { //Также делаем копию из которого удалена какая то группа
    Groups groups = new Groups(this);//создаем копию - новое множество с помощью конструктора
    groups.remove(group); //из этой копии удаляем объект
    return groups; //возвращаем эту копию с удаленной группой
  }


}
