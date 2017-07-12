package jp.gr.java_conf.hirady.blog.domain.model;

import lombok.Data;

@Data
public class User {

  private String id;

  private String name;

  private String password;

  private String explain;

  private String image;

  private String email;

}
