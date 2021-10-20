package ru.stqa.pft.mantis.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

  public class Issues extends ForwardingSet<IssueInfo> { // 5.6. с 04.10

    private Set<IssueInfo> delegate;

    public Issues(ru.stqa.pft.mantis.model.Issues issues) {
      this.delegate = new HashSet<IssueInfo>(issues.delegate);
    }

    public Issues() {
      this.delegate = new HashSet<IssueInfo>();
    }

    public Issues(Collection<IssueInfo> issues) { //см 7.4. 06.30
      this.delegate = new HashSet<IssueInfo>(issues);
    }


    @Override //обязательный метод библиотеки ForwardingSet
    protected Set<IssueInfo> delegate() {
      return delegate;
    }

  }