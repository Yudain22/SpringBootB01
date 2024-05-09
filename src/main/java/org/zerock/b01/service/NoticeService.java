package org.zerock.b01.service;

import org.zerock.b01.dto.NoticeDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

import java.util.List;

public interface NoticeService {
  Long register(NoticeDTO noticeDTO);
  NoticeDTO readOne(Long no);
  void modify(NoticeDTO noticeDTO);
  void remove(Long no);
  List<NoticeDTO> list(PageRequestDTO pageRequestDTO);
}
















