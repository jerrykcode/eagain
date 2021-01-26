package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.dto.TagsDTO;
import com.jerrykcode.eagain.mapper.TagMapper;
import com.jerrykcode.eagain.model.Tag;
import com.jerrykcode.eagain.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagsDTO> listAll() {
        List<Tag> tags = tagMapper.listAll();
        Map<String, TagsDTO> tagsDTOMap = new HashMap<>();
        tags.forEach(tag->{
            if (!tagsDTOMap.containsKey(tag.getType())) {
                tagsDTOMap.put(tag.getType(),
                        new TagsDTO().setType(tag.getType()).setTags(new ArrayList<>()));
            }
            tagsDTOMap.get(tag.getType()).getTags().add(tag);
        });
        return new ArrayList<TagsDTO>(tagsDTOMap.values());
    }
}
