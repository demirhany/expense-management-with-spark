package com.example.big_data_aws.controller;

import com.example.big_data_aws.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("employees", mainService.getAllEmployeesWithUrlPhotos());
        return "index_with_url_photos";
    }
}
