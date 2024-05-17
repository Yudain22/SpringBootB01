package org.zerock.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Member;
import org.zerock.b01.repository.MemberRepository;
import org.zerock.b01.security.dto.MemberSecurityDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
//    private PasswordEncoder passwordEncoder;

//    public CustomUserDetailsService(){
//        this.passwordEncoder =new BCryptPasswordEncoder();
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername : " + username);

        //데이터베이스에서 username으로 검색한 회원정보를 취득
        Optional<Member> result = memberRepository.getWithRoles(username);

        //회원정보 유무를 확인하는 if문
        if(result.isEmpty()){
            //isEmpty 비어있으면 UsernameNotFoundException 예외처리 후 진행 안 됨
            throw new UsernameNotFoundException("username not found...");
        }

        //데이터가 있으면 멤버의 데이터를 저장
        Member member = result.get();

        //Member객체를 MemberSecurityDTO객체로 변환
        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getMid(),
                        member.getMpw(),
                        member.getEmail(),
                        member.isDel(),
                        false,
                        member.getRoleSet()
                                //저장된 memberRole은 USER,ADMIN 두가지 결과가 리스트로 저장됨
                                .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+ memberRole.name()))
                                .collect(Collectors.toList())
                );

        log.info("memberSecurityDTO");
        log.info(memberSecurityDTO);

        return memberSecurityDTO;

//        UserDetails userDetails = User.builder()
//                .username(username)
//                .password(passwordEncoder.encode("1111"))
//                .authorities("ROLE_USER")
//                .build();
//
//        return userDetails;
    }
}
