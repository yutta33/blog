package jp.gr.java_conf.hirady.blog.app.controller.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.gr.java_conf.hirady.blog.ApplicationException;
import jp.gr.java_conf.hirady.blog.domain.model.Entry;
import jp.gr.java_conf.hirady.blog.domain.service.EntryService;

@Controller
@RequestMapping("/mn")
public class EntryController {

  static final Logger logger = LoggerFactory.getLogger(EntryController.class);

  String root = System.getProperty("user.dir");
  String dir = "/files";

  @Inject
  EntryService entryService;

  @RequestMapping(method = RequestMethod.GET)
  public String init() {

    logger.debug("init !!!");

    return "manager/index";
  }

  @RequestMapping(value = "entries")
  public String entries(Model model) throws Exception {

    List<Entry> entries = entryService.getEntries();

    model.addAttribute("entries", entries);

    return "manager/entry/entries";
  }

  @RequestMapping(value = "edit", method = RequestMethod.GET)
  public String edit(EntryForm entryForm,
      @RequestParam(value = "entry", required = false) String entry,
      Model model) throws Exception {

    if (entry != null && !entry.equals("")) {
      Entry item = entryService.findEntry(entry);
      BeanUtils.copyProperties(item, entryForm);
    }

    model.addAttribute(entryForm);

    return "manager/entry/edit";
  }

  @RequestMapping(value = "edit", method = RequestMethod.POST)
  public String entry(@Validated EntryForm entryForm, BindingResult result, Model model) throws Exception {

    if (result.hasErrors()) {
      model.addAttribute(entryForm);
      return "manager/entry/edit";
    }

    if(entryForm.getFile() != null && !entryForm.getFile().isEmpty()){
      //ファイルアップロード
      upload(entryForm.getFile());
    }

    // 登録
    Entry entry = new Entry();
    BeanUtils.copyProperties(entryForm, entry);
    entryService.entry(entry);

    return "redirect:/mn/entries";
  }

  @RequestMapping(value = "delete")
  public String delete(
      @RequestParam(value = "entry", required = true) String entry) throws Exception {

    entryService.delete(entry);

    return "redirect:/mn/entries";
  }

 public void upload(@RequestParam MultipartFile file) throws ApplicationException
 {
   try {
     InputStream in = file.getInputStream();

     File uploadfile = new File(dir);
     if (!uploadfile.exists()) {
       uploadfile.mkdirs();
     }

     String path = root + dir + "/" + file.getOriginalFilename();
     uploadfile = new File(path);
     FileCopyUtils.copy(in, new FileOutputStream(uploadfile));

   } catch (IOException e) {
     throw new RuntimeException("Error uploading file.", e);
   }
 }



}