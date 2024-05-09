package org.zerock.b01.service;

import org.zerock.b01.dto.*;

public interface BoardService {
  Long register(BoardDTO boardDTO);
  BoardDTO readOne(Long bno);
  void modify(BoardDTO boardDTO);
  void remove(Long bno);
  PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
  // 게시글 목록에 댓글 갯수 표시 하기.
  PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

  PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);
}
