package jp.gr.java_conf.hirady.blog.domain.mapper;

import java.util.List;

import jp.gr.java_conf.hirady.blog.domain.model.Entry;

public interface EntryMapper {

  public List<Entry> select(Entry entry);

  public void insert(Entry entry);

  public void update(Entry entry);

  public void delete(String id);

}