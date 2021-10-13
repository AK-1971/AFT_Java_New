package ru.stqa.pft.mantis.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Users extends ForwardingSet<UserData> { // 5.6. с 04.10

  private Set<UserData> delegate;

  public Users(Users users) {
    this.delegate = new HashSet<UserData>(users.delegate);
  }

  public Users() {
    this.delegate = new HashSet<UserData>();
  }

  public Users(Collection<UserData> users) { //см 7.4. 06.30
    this.delegate = new HashSet<UserData>(users);
  }


  @Override
  protected Set<UserData> delegate() {
    return delegate;
  }
}
