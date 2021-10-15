package com.brightics.prj.web.service;

import com.brightics.prj.web.entity.Member;
import com.brightics.prj.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate= new DefaultOAuth2UserService();
        OAuth2User oAuth2User= delegate.loadUser(userRequest);
        Map<String, Object> userInfo= oAuth2User.getAttribute("kakao_account");


        Object oauthId=oAuth2User.getAttribute("id");
  
        Member member =memberRepository.findMemberByOauthId(oauthId.toString());
        if (member==null){
            member= new Member();
        }
        member.setOauthId(oauthId.toString());
        member.setEmailVerified(true);
        memberRepository.save(member);

        return oAuth2User;
    }

}
