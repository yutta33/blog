package jp.gr.java_conf.hirady.blog.app.controller.manager;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class EntryForm {

  private String id;

  @NotBlank
  private String title;

  @NotBlank
  private String body;

  private String category;

  private boolean draft;

  private String insertTime;

  private String updateTime;

}
