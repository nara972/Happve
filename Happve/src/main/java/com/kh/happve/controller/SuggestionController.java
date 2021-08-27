package com.kh.happve.controller;

import com.kh.happve.dto.SignUpForm;
import com.kh.happve.dto.SuggestionForm;
import com.kh.happve.entity.Suggest;
import com.kh.happve.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/suggest")
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping("")
    public String suggestionForm(Model model) {
        model.addAttribute(new SuggestionForm());
        return "suggestion";
    }

    @PostMapping("")
    public String saveSuggestion(SuggestionForm suggestionForm, RedirectAttributes redirectAttributes) {
        Long savedSuggestionId = suggestionService.save(suggestionForm);
        if(savedSuggestionId > 0) {
            redirectAttributes.addAttribute("status",true);
        }
        return "redirect:/suggest";
    }


}
