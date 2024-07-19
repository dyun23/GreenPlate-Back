package com.team404x.greenplate.keyword.controller;


import com.team404x.greenplate.config.SecuredOperation;
import com.team404x.greenplate.keyword.service.KeywordService;

import io.swagger.v3.oas.annotations.Operation;
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

    @SecuredOperation
    @Operation(summary = "[관리자] 키워드를 생성하는 API")
    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public ResponseEntity<String> createKeyword(String keyword) {
        keywordService.create(keyword);
        return ResponseEntity.ok(keyword);
    }

    @Operation(summary = "[전체] 키워드 목록을 가져오는 API")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<List<String>> getKeywords() {
        return ResponseEntity.ok(keywordService.getKeywords());
    }

    @SecuredOperation
    @Operation(summary = "[관리자] 키워드 삭제 API")
    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public ResponseEntity<String> deleteKeyword(String keyword) {
        keywordService.deleteKeywords(keyword);
        return ResponseEntity.ok(keyword);
    }
}
