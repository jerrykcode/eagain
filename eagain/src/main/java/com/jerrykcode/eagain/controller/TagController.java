package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.dto.TagsDTO;
import com.jerrykcode.eagain.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public List<TagsDTO> list() {
        return tagService.listAll();
    }
}
