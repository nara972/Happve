package com.kh.happve.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.happve.dto.SignUpForm;
import com.kh.happve.entity.Member;
import com.kh.happve.service.MemberService;
import com.kh.happve.validator.CurrentAccount;
import com.kh.happve.validator.SignUpFormValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final SignUpFormValidator signUpFormValidator;
	private final MemberService memberService;
	
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
	public String SignUp(@Valid SignUpForm signUpForm,Errors errors) {
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

}