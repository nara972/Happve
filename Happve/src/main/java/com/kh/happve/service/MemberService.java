package com.kh.happve.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kh.happve.config.AppProperties;
import com.kh.happve.dto.EmailMessage;
import com.kh.happve.dto.SignUpForm;
import com.kh.happve.entity.Member;
import com.kh.happve.repository.MemberRepository;
import com.kh.happve.validator.UserMember;

import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final AppProperties appProperties;
	private final TemplateEngine templateEngine;
	private final EmailService emailService;
	
	public Member saveNewMember(@Valid SignUpForm signUpForm) {
		
		Member member=Member.builder()
	                        .email(signUpForm.getEmail())
	                        .nickname(signUpForm.getNickname())
	                        .password(passwordEncoder.encode(signUpForm.getPassword()))
	                        .vtype(signUpForm.getVtype())
	                        .role("ROLE_USER")
	                        .build();
		member.generateEmailCheckToken();
		return memberRepository.save(member);
	}

	public void login(Member member) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(new UserMember(member),
				member.getPassword(), List.of(new SimpleGrantedAuthority(member.getRole())));
		SecurityContextHolder.getContext().setAuthentication(token);
	}
	
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
		Member member=memberRepository.findByEmail(emailOrNickname);
		if(member==null) {
			member=memberRepository.findByNickname(emailOrNickname);
		}
		if(member==null) {
			throw new UsernameNotFoundException(emailOrNickname);
		}
		UserMember userMember=new UserMember(member);
		return userMember;  
	}
	
	    //프로필 변경
		public void updateProfile(Member member,String email,String nickname,String vtype) {
			member.setEmail(email);
			member.setNickname(nickname);
			member.setVtype(vtype);
			memberRepository.save(member);
		}
		
		//비밀번호 변경
		public void updatePassword(Member member, String newPassword) {
			member.setPassword(passwordEncoder.encode(newPassword));
			memberRepository.save(member);
		}

		//로그인 링크 보내기
		public void sendLoginLink(Member member) {
			Context context = new Context();
			context.setVariable("link", "/check-email-token?token="+
			        member.getEmailCheckToken()+"&email="+member.getEmail());
			context.setVariable("nickname", member.getNickname());
			context.setVariable("linkName", "이메일로 로그인하기");
	        context.setVariable("message", "로그인 하려면 아래 링크를 클릭하세요");
	        context.setVariable("host", appProperties.getHost());
	        
	        // simple-link.html을 String타입으로 생성
	        String message=templateEngine.process("simple-link", context);
	        
	        EmailMessage emailMessage=EmailMessage.builder()
	        		.to(member.getEmail())
	        		.subject("Happve, 로그인 링크")
	        		.message(message)
	        		.build();
	        emailService.sendEmail(emailMessage);
	    
		}

}