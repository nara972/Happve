package com.kh.happve.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.happve.dto.PasswordForm;
import com.kh.happve.dto.ProfileForm;
import com.kh.happve.dto.SignUpForm;
import com.kh.happve.entity.Member;
import com.kh.happve.repository.MemberRepository;
import com.kh.happve.service.MemberService;
import com.kh.happve.validator.CurrentAccount;
import com.kh.happve.validator.PasswordFormValidator;
import com.kh.happve.validator.ProfileFormValidator;
import com.kh.happve.validator.SignUpFormValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	
	private final SignUpFormValidator signUpFormValidator;
	private final ProfileFormValidator profileValidator;
	private final PasswordFormValidator passwordValidator;
	private final MemberService memberService;
	private final MemberRepository memberRepository;
	
	@GetMapping("/admin")
	public String adminPage(@CurrentAccount Member member) {
		return "admin";
	}
	
	@GetMapping("/signup")
	public String SignUpForm(Model model) {
		model.addAttribute(new SignUpForm());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String SignUp(@Valid SignUpForm signUpForm, Errors errors) {
		if(errors.hasErrors()) {
			return "signup";
		}
		signUpFormValidator.validate(signUpForm,errors);
		if(errors.hasErrors()) {
			return "signup";
		}
		
		Member member=memberService.saveNewMember(signUpForm);
		memberService.login(member);
		
		return "redirect:/";
	}
	
	//마이페이지-프로필 변경
			@GetMapping("/mypage/updateprofile")
			public String updateProfileForm(@CurrentAccount Member member,Model model) {
				model.addAttribute(member);
				model.addAttribute("profileForm",new ProfileForm());
				return "updateprofile";
			}
			
			@PostMapping("/mypage/updateprofile")
			public String updateProfile(@CurrentAccount Member member,@Valid ProfileForm profileForm,Errors errors,
					Model model) {
				if(errors.hasErrors()) {
					return "updateprofile";
				}
				profileValidator.validate(profileForm, errors);
				if(errors.hasErrors()) {
					return "updateprofile";
				}
				memberService.updateProfile(member, profileForm.getEmail(), profileForm.getNickname(), profileForm.getVtype());
				model.addAttribute(member);
				return "redirect:/mypage";
			}
			
			//마이페이지-비밀번호 변경
			@GetMapping("/mypage/updatepassword")
			public String updatePasswordForm(@CurrentAccount Member member,Model model) {
				model.addAttribute("passwordForm",new PasswordForm());
				return "updatepassword";
			}
			
			@PostMapping("/mypage/updatepassword")
			public String updatePassword(@CurrentAccount Member member, @Valid PasswordForm passwordForm, Errors errors,
					Model model) {
				if(errors.hasErrors()) {
					return "updatepassword";
				}
				passwordValidator.validate(passwordForm, errors);
				if(errors.hasErrors()) {
					return "updatepassword";
				}
				
				memberService.updatePassword(member, passwordForm.getNewPassword());
				return "redirect:/mypage";
			}
			
			//비밀번호 없이 로그인하기
			@GetMapping("/email-login")
			public String emailLoginForm() {
				return "email-login";
			}
			
			@PostMapping("/email-login")
			public String sendEmailLoginLink(String email,Model model,RedirectAttributes attributes) {
				Member member=memberRepository.findByEmail(email);
				if(member==null) {
					model.addAttribute("error","유효한 이메일이 아닙니다.");
					return "email-login";
				}
				memberService.sendLoginLink(member);
				attributes.addFlashAttribute("message","이메일 인증 메일을 발송했습니다.");
				return "redirect:/email-login";
			}
		
			@GetMapping("/check-email-token")
			public String checkEmailToken(String token,String email,Model model) {
				Member member=memberRepository.findByEmail(email);
				memberService.login(member);
				return "index";
			}
			

}