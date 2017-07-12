package jp.gr.java_conf.hirady.blog.app.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class EntryController {

  
  @RequestMapping(value="list")
  public String list(){
    
    return "";
  }
  
  
  public String add(){
    
    return "";
  }
  
  
  public String edit(){
    
    return "";
  }
  
  public String delete(){
    
    return "";
  }
  
  public String confirm(){
    
    return "";
  }
  
  
}
