package org.zerock.b01.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
  private final ModelMapper modelMapper;
  private final BoardRepository boardRepository;

  @Override
  public Long register(BoardDTO boardDTO) {
    Board board = modelMapper.map(boardDTO, Board.class);
    Long bno = boardRepository.save(board).getBno();
    return bno;
  }

  @Override
  public BoardDTO readOne(Long bno) {
    Optional<Board> result = boardRepository.findById(bno);
    Board board = result.orElseThrow();
    BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
    return boardDTO;
  }

  @Override
  public void modify(BoardDTO boardDTO) {
    Optional<Board> result = boardRepository.findById(boardDTO.getBno());
    Board board = result.orElseThrow();
    board.change(boardDTO.getTitle(), boardDTO.getContent());
    boardRepository.save(board);
  }

  @Override
  public void remove(Long bno) {
    boardRepository.deleteById(bno);
  }

  @Override
  public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
    //페이지 및 검색 조건 취득
    String[] types = pageRequestDTO.getTypes();
    String keyword = pageRequestDTO.getKeyword();
    Pageable pageable = pageRequestDTO.getPageable("bno");
    //레포지토리를 실행하여 데이터 취득
    Page<Board> result = boardRepository.searchAll(types,keyword,pageable);
    // VO를 DTO로 변환
     List<BoardDTO>dtoList = result.getContent().stream()
        .map(board -> modelMapper.map(board, BoardDTO.class))
        .collect(Collectors.toList());

    return PageResponseDTO.<BoardDTO>withAll()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(dtoList)
        .total((int)result.getTotalElements())
        .build();
  }

  // 게시글 목록 조회시 , 댓글 갯수 같이 출력
  @Override
  public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
    //페이지 및 검색 조건 취득
    String[] types = pageRequestDTO.getTypes();
    String keyword = pageRequestDTO.getKeyword();
    Pageable pageable = pageRequestDTO.getPageable("bno");
    //레포지토리를 실행하여 데이터 취득
    Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types,keyword,pageable);

    // VO를 DTO로 변환, 원래는 엔티티 클래스 타입 -> DTO 타입으로 수동으로 변환.
//    List<BoardDTO>dtoList = result.getContent().stream()
//        .map(board -> modelMapper.map(board, BoardDTO.class))
//        .collect(Collectors.toList());
    // 우리는 자동으로 Projections 이용해서, 미리 자동으로 타입을 변환 하게 설정 해둠.

    return PageResponseDTO.<BoardListReplyCountDTO>withAll()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(result.getContent())
        .total((int)result.getTotalElements())
        .build();
  }
}




















