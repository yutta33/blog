package jp.gr.java_conf.hirady.blog;

public class ApplicationException extends RuntimeException{

  Throwable cause;
  Object[] args;

  public ApplicationException(Throwable cause, String ...args) {

  }

}
