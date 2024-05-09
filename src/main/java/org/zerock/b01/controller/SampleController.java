package org.zerock.b01.controller;

import jakarta.validation.Valid;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.NoticeDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.service.NoticeService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
public class SampleController {
  @Autowired
  private NoticeService noticeService;

  @GetMapping("/hello")
  public void hello(Model model) {
    log.info("hello......................");
    model.addAttribute("msg", "HELLO WORLD");
  }
  @GetMapping("/ex/ex1")
  public void ex1(Model model) {
    List<String> list = Arrays.asList("AAA","BBB","CCC","DDD");
    model.addAttribute("list", list);
  }

  @ToString
  class SampleDTO{
    private String p1,p2,p3;
    public String getP1(){
      return p1;
    }
    public String getP2(){
      return p2;
    }
    public String getP3(){
      return p3;
    }
  }
  @GetMapping("/ex/ex2")
  public void ex2(Model model) {
    log.info("ex/ex2......................");
    List<String> strList = IntStream.range(1,10)
        .mapToObj(i->"Data"+i)
        .collect(Collectors.toList());
    model.addAttribute("list", strList);

    Map<String,String> map = new HashMap<>();
    map.put("A","AAAA");
    map.put("B","BBBB");
    model.addAttribute("map", map);

    SampleDTO sampleDTO = new SampleDTO();
    sampleDTO.p1 = "Value -- p1";
    sampleDTO.p2 = "Value -- p2";
    sampleDTO.p3 = "Value -- p3";
    model.addAttribute("dto", sampleDTO);
  }
  @GetMapping("/ex/ex3")
  public void ex3(Model model) {
    model.addAttribute("arr", new String[]{"AAA","BBB","CCC","DDD"});
  }

  @GetMapping("/ex/index")
  public void index(Model model) {
  }
  @GetMapping("/ex/join")
  public void join(Model model) {
  }
  @GetMapping("/ex/login")
  public void login(Model model) {
  }
  @GetMapping("/ex/mypage")
  public void mypage(Model model) {
  }
  @GetMapping("/ex/notice_list")
  public void notice_list(PageRequestDTO pageRequestDTO, Model model) {
    model.addAttribute("noticeList", noticeService.list(pageRequestDTO)) ;
  }
  @GetMapping("/ex/notice_add")
  public void notice_addGet(Model model) {
  }
  @PostMapping("/ex/notice_add")
  public String notice_addPost(NoticeDTO noticeDTO
      , RedirectAttributes redirectAttributes
      , Model model) {
    Long no = noticeService.register(noticeDTO);
    redirectAttributes.addFlashAttribute("no",no);
    return "redirect:/ex/notice_list";
  }
  @PostMapping("/ex/modify")
  public String notice_modify(PageRequestDTO pageRequestDTO,
                              @Valid NoticeDTO noticeDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
    if(bindingResult.hasErrors()) {
      log.info("has errors.......");
      String link = pageRequestDTO.getLink();
      redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
      redirectAttributes.addAttribute("no",noticeDTO.getNo());
      return "redirect:/ex/notice_modify?no="+noticeDTO.getNo();
    }
    noticeService.modify(noticeDTO);
    return "redirect:/ex/notice_view?no="+noticeDTO.getNo();
  }
  @PostMapping("/ex/notice_remove")
  public String notice_remove(Long no,Model model) {
    noticeService.remove(no);
    return "redirect:/ex/notice_list";
  }
  @GetMapping({"/ex/notice_view","/ex/notice_modify"})
  public void notice_view(Long no,Model model) {
    NoticeDTO dto = noticeService.readOne(no);
    model.addAttribute("notice", dto) ;
  }
  @GetMapping("/ex/program")
  public void program(Model model) {
  }
}




















