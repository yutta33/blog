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

    if (entries != null) {
      model.addAttribute("entry", entries.get(0));
    }
    if (entries.size() > 1) {
      model.addAttribute("prev", entries.get(1).getId());
    }

    return "general/blog";
  }

  @RequestMapping(value = "entry/{id}", method = RequestMethod.GET)
  public String blog(@PathVariable String id, Model model) throws Exception {

    Entry entry = entryService.findEntry(id);

    if(entry == null) {
      return "general/blog";
    }

    model.addAttribute("entry", entry);

    List<Entry> entries = entryService.getEntries();

    for(int i = 0; i< entries.size(); i++) {

      Entry current = entries.get(i);
      if(entry.getId().equals(current.getId())) {

        if(i > 0) {
          model.addAttribute("next", entries.get( i -1 ).getId());
        }

        if(i + 1 < entries.size()) {
          model.addAttribute("prev", entries.get( i + 1 ).getId());
        }

        break;
      }
    }

    return "general/blog";
  }

}