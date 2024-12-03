package com.ozo.bigdatahadoopspring.controller;

import com.ozo.bigdatahadoopspring.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/")
    public String fast(Model model) {
        model.addAttribute("employees", mainService.getAllEmployeesWithUrlPhotos());

        return "index_with_url_photos";
    }

    @GetMapping("/base64")
    public String index(Model model) {
        model.addAttribute("employees", mainService.getAllEmployeesWithBase64Photos());
        return "index_with_base64_photos";
    }
}
