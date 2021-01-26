package com.jerrykcode.eagain.service;

import com.jerrykcode.eagain.dto.TagsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    List<TagsDTO> listAll();
}
