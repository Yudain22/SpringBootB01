package org.zerock.b01.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.service.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor

public class BoardController {

  // 게시글에 댓글 갯수 표현하는 목록 출력으로 변경함.
  // 주석으로 내용 변경 확인 테스트
  private final BoardService boardService;
  @GetMapping("/list")
  public void list(PageRequestDTO pageRequestDTO, Model model) {
    // 타입 변경 : BoardListReplyCountDTO, 메서드 명 변경.
    PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
//    log.info(responseDTO);
    model.addAttribute("responseDTO", responseDTO);
  }
  @GetMapping("/register")
  public void registerGET(){}

  @PostMapping("/register")
  public String registerPOST(@Valid BoardDTO boardDTO
                            , BindingResult bindingResult
                            , RedirectAttributes redirectAttributes) {
    log.info("board Post register.......");
    if(bindingResult.hasErrors()) {
      log.info("has errors.......");
      redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
      return "redirect:/board/register";
    }
    log.info(boardDTO);
    Long bno = boardService.register(boardDTO);
    redirectAttributes.addFlashAttribute("result",bno);
    return "redirect:/board/list";
  }
  @GetMapping({"/read","/modify"})
  public void read(Long bno, PageRequestDTO pageRequestDTO,Model model) {
    BoardDTO boardDTO = boardService.readOne(bno);
    log.info(boardDTO);
    model.addAttribute("dto",boardDTO);
  }

  @PostMapping("/modify")
  public String modify( PageRequestDTO pageRequestDTO,
                        @Valid BoardDTO boardDTO
                        , BindingResult bindingResult
                        , RedirectAttributes redirectAttributes) {
    log.info("board Modify register.......");
    if(bindingResult.hasErrors()) {
      log.info("has errors.......");
      String link = pageRequestDTO.getLink();
      redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
      redirectAttributes.addAttribute("bno",boardDTO.getBno());
      return "redirect:/board/modify?"+link;
    }
    System.out.println(boardDTO.getBno());
    boardService.modify(boardDTO);
    redirectAttributes.addFlashAttribute("result","modified");
    redirectAttributes.addAttribute("bno",boardDTO.getBno());
    return "redirect:/board/read";
  }
  @PostMapping("/remove")
  public String remove(Long bno, RedirectAttributes redirectAttributes) {
    log.info("board Remove register.......");
    boardService.remove(bno);
    redirectAttributes.addFlashAttribute("result","removed");
    return "redirect:/board/list";
  }
}


















