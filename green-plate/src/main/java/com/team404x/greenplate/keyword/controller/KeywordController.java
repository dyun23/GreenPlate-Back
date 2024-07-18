package com.team404x.greenplate.keyword.controller;


import com.team404x.greenplate.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword")
public class KeywordController {
    private final KeywordService keywordService;
    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public ResponseEntity<String> createKeyword(String keyword) {
        keywordService.create(keyword);
        return ResponseEntity.ok(keyword);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<List<String>> getKeywords() {
        return ResponseEntity.ok(keywordService.getKeywords());
    }
    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public ResponseEntity<String> deleteKeyword(String keyword) {
        keywordService.deleteKeywords(keyword);
        return ResponseEntity.ok(keyword);
    }
}
