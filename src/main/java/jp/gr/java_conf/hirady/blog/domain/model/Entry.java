package jp.gr.java_conf.hirady.blog.domain.model;

import lombok.Data;

@Data
public class Entry {

  private String title;

  private String honbun;

  private String insertTime;

  private String lastUpdateTime;

  private String tag;

}
