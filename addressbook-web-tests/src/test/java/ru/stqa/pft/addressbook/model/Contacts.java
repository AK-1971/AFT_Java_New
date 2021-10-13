package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Contacts extends ForwardingSet<ContactData> { //5.6. с 04.10

  private Set<ContactData> delegate; //создаем объект которому будут делегированы стандартные методы GroupData + наши новые

  public Contacts(Contacts contacts) {
    this.delegate = new HashSet<ContactData>(contacts.delegate);
  }

  public Contacts() {
    this.delegate = new HashSet<ContactData>();
  }

  public Contacts(Collection<ContactData> contacts) { //7.4. на 06.50
    this.delegate = new HashSet<ContactData>(contacts);
  }


  @Override
  protected Set<ContactData> delegate() {
    return delegate;
  }

  public Contacts withAdded(ContactData contact) { //в этом методе мы делаем копию delegate и добавляем в нее объекта (group)- это даст возможность работать и с новым объектом и старым, неизменённым множеством
    Contacts contacts = new Contacts(this);//создаем копию - новое множество с помощью конструктора
    contacts.add(contact); //в эту копию добавляем объект
    return contacts; //возвращаем эту копию с добавленной группой
  }

  public Contacts withOut(ContactData contact) { //Также делаем копию из которого удалена какая то группа
    Contacts contacts = new Contacts(this);//создаем копию - новое множество с помощью конструктора
    contacts.remove(contact); //из этой копии удаляем объект
    return contacts; //возвращаем эту копию с удаленной группой
  }

}
