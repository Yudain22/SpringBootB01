package org.zerock.b01.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SampleJSONController {
  @Tag(name = "스웨거 ui 화면에 나타나는지 여부 확인 테스트", description = "스웨거 ui 화면에 나타나는지 여부 확인 테스트")
  @GetMapping("/helloArr")
  public String[] helloArr(){
    log.info("helloArr................");
    // 화면을 찾는것 아니라 JSON데이터를 반환
    return new String[]{"AAA","BBB","CCC"};
  }

  @Tag(name = "샘플로 테스트2", description = "오늘 점심 먹고 싶은 메뉴 작성 해보기")
  @GetMapping("/lunchMenu")
  public String[] lunchMenu(){
    log.info("lunchMenu................");
    // 화면을 찾는것 아니라 JSON데이터를 반환
    return new String[]{"돼지국밥","햄버거","피자"};
  }
}
