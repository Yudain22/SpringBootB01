package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.zerock.b01.domain.Member;
import org.zerock.b01.domain.MemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers(){
        //IntStream 1~100까지 반복문을 위해 사용
        IntStream.rangeClosed(1,100).forEach(i->{
            //회원 생성 1~100
            Member member = Member.builder()
                    .mid("member" + i )
                    //CustomUserDetailsService에서 BCryptPasswordEncoder했기 때문에 passwordEncoder는 암호화 됨
                    //encode 때문에 다 다르게 나옴
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email"+i+"@aaa.bbb")
                    .build();

            //일반 회원의 권한 설정
            //멤버 유저 추가 1~100까지는 멤버 유저 권한
            member.addRole(MemberRole.USER);

            //관리자 권한 설정
            //90이상의 사람들은 ADMIN 권한 사용도 가능
            //member_role_set을 보면 90이상은 0,1권한 모두를 가짐
            if (i >= 90){
                member.addRole(MemberRole.ADMIN);
            }
            //데이터베이스에 저장
            //save 데이터가 없으면 insert 있으면 update
            memberRepository.save(member);
        });
    }

    @Test
    public void testRead(){
        //데이터 베이스에서 mid를 기준으로 데이터를 취득
        Optional<Member> result = memberRepository.getWithRoles("member100");

        //에러 확인
        Member member = result.orElseThrow();

        //전체 데이터 출력
        log.info(member);
        //role 데이터 출력
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
    }

    @Commit
    @Test
    public void testUpdate(){
        String mid = "유다인";
        String mpw = passwordEncoder.encode("543211");

        memberRepository.updatePassword(mpw,mid);
    }
}
