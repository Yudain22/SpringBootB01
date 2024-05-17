package org.zerock.b01.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
//extends는 MemberSecurityDTO가 User를 상속받고 있음을 의미
public class MemberSecurityDTO extends User implements OAuth2User {
    //멤버 변수 설정
    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;

    private Map<String, Object> props;

    //생성자 : MemberSecurityDTO 객체를 만들기 위해 생성
    //생성자 안 만들면 기본 생성자 private Member = new member(); 형태의  기본생성자 생김
    public MemberSecurityDTO(String username, String password, String email, boolean del, boolean social, Collection<? extends GrantedAuthority> authorities)
    {
        //super는 부모 클래스의 생성자를 부르는 것 여기서는 User의 생성자 부름
        super(username, password, authorities);

        //객체 안의 멤버 변수에 각각의 데이터 설정부분
        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.props;
    }

    @Override
    public String getName(){
        return this.mid;
    }
}
