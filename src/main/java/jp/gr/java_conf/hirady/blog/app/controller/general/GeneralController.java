package jp.gr.java_conf.hirady.blog.app.controller.general;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.gr.java_conf.hirady.blog.domain.model.Entry;
import jp.gr.java_conf.hirady.blog.domain.service.EntryService;

@Controller
@RequestMapping("/")
public class GeneralController {

  static final Logger logger = LoggerFactory.getLogger(GeneralController.class);

  @Inject
  EntryService entryService;

  @RequestMapping(method = RequestMethod.GET)
  public String init(Model model) throws Exception {

    List<Entry> entries = entryService.getEntries();

    if(entries != null && entries.size() > 0) {
        model.addAttribute("entry", entries.get(0));
    }

    return "general/blog";
  }

  @RequestMapping(value="entry/{id}", method = RequestMethod.GET)
  public String blog(@PathVariable String id, Model model) throws Exception{

    Entry entry = entryService.findEntry(id);
    model.addAttribute("entry", entry);

    return "general/blog";
  }

}