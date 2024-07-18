package com.team404x.greenplate.keyword.service;

import com.team404x.greenplate.keyword.entity.Keyword;
import com.team404x.greenplate.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public void create(String keyword) {
        keywordRepository.save(Keyword.builder().name(keyword).build());
    }

    public List<String> getKeywords() {
        List<Keyword> keywords = keywordRepository.findAll();
        List<String> keywordList = new ArrayList<>();
        for (Keyword keyword : keywords) {
            keywordList.add(keyword.getName());
        }
        return keywordList;
    }

    public void deleteKeywords(String keyword) {
        keywordRepository.deleteByName(keyword);
    }
}
