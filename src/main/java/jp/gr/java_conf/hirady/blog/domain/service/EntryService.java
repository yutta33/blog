package jp.gr.java_conf.hirady.blog.domain.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.gr.java_conf.hirady.blog.domain.mapper.EntryMapper;
import jp.gr.java_conf.hirady.blog.domain.model.Entry;

@Service
@Transactional(readOnly = true)
public class EntryService {

  @Inject
  EntryMapper entryMapper;

  public List<Entry> getEntries() throws Exception {

    return entryMapper.selectAll();

  }

  public Entry findEntry(String id) throws Exception {

    return entryMapper.select(id);

  }

  @Transactional(readOnly = false)
  public void entry(Entry entry) throws Exception {

    if (entry.getId() == null || entry.getId().equals("")) {
      // 新規
      entry.setId(this.createId());
      entryMapper.insert(entry);
    } else {
      // 更新
      Entry findEntry = entryMapper.select(entry.getId());
      if (findEntry != null) {
        entryMapper.update(entry);
      } else {
        // 更新対象無エラ-
      }
    }
  }

  @Transactional(readOnly = false)
  public void delete(String id) throws Exception {

    entryMapper.delete(id);

  }

  private String createId() {

    LocalDateTime d = LocalDateTime.now();
    DateTimeFormatter f = DateTimeFormatter.ofPattern("SSSssmmHHddyyyyMM");

    return d.format(f);
  }
}
