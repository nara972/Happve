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
	
	public Member saveNewMember(@Valid SignUpForm signUpForm) {
		Member member=Member.builder()
	                        .email(signUpForm.getEmail())
	                        .nickname(signUpForm.getNickname())
	                        .password(passwordEncoder.encode(signUpForm.getPassword()))
	                        .vtype(signUpForm.getVtype())
	                        .role("ROLE_USER")
	                        .build();
		
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

}