package com.example.totalexpenseapi.controller;

import com.example.totalexpenseapi.entity.Expense;
import com.example.totalexpenseapi.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> hello() {
        mainService.getAllExpenses();
        return ResponseEntity.ok(mainService.getAllExpenses());
    }

    @GetMapping("/expenses/{userid}")
    public ResponseEntity<Float> getExpenseByUserId(@PathVariable Long userid) {
        return ResponseEntity.ok(mainService.getExpenseByUsername(userid));
    }
}
