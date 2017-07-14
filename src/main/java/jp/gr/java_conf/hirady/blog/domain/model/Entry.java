package jp.gr.java_conf.hirady.blog.domain.model;

import lombok.Data;

@Data
public class Entry {

  private String id;

  private String title;

  private String body;

  private String category;

  private boolean draft;

  private String insertTime;

  private String updateTime;

}