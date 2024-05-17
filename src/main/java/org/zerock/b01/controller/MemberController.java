package org.zerock.b01.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.MemberJoinDTO;
import org.zerock.b01.service.MemberService;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public void loginGET(String errorCode, String logout){
        log.info("login get ........");
        log.info("logout:" +logout);

        if(logout != null){
            log.info("user logout...");
        }
    }

    @GetMapping("/join")
    public void joinGET(){

        log.info("join get........");
    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes){

        log.info("join post........");
        log.info("memberJoinDTO:" +memberJoinDTO);

        try {
            //회원가입 서비스 실행부분
            memberService.join(memberJoinDTO);
            //위 조인이 실행되지 않을 때(아이디가 존재할 경우 에러 발생) 처리하는 것이 아래부분
        }catch (MemberService.MidExistException e){
            //에러 발생시 리다이렉트 페이지에 err=mid 결과를 가지고 감
            redirectAttributes.addFlashAttribute("error","mid");
            return "redirect:/member/join";
        }

        redirectAttributes.addFlashAttribute("result ","success");
        return "redirect:/member/login";
    }
}
