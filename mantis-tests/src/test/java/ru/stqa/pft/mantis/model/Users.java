package ru.stqa.pft.mantis.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Users extends ForwardingSet<UserData> { // 5.6. с 04.10

  private Set<UserData> delegateObject;

  public Users(Users users) {
    this.delegateObject = new HashSet<UserData>(users.delegateObject);
  }

  public Users() {
    this.delegateObject = new HashSet<UserData>();
  }

  public Users(Collection<UserData> users) { //см 7.4. 06.30
    this.delegateObject = new HashSet<UserData>(users);
  }


  @Override
  protected Set<UserData> delegate() {
    return delegateObject;
  }
}
